#include <sstream>
#include <iostream>
#include <climits>
#include <cstdio>
#include <string>

#include <impl/basic/include.hpp>
#include <algorithms/cyclic_connectivity.hpp>
#include <graphs.hpp>
#include <io.hpp>
#include <io/graph6.hpp>
#include <io/oldBA.hpp>
#include <io/print_nice.hpp>
#include <multipoles.hpp>
#include <operations/basic.hpp>
#include <util/cxxopts.hpp>


using namespace ba_graph;

cxxopts::Options options("showcutgraph",
		"\nTransforms g6/oldBA graph representation with smallest cut into a pdf picture using graphviz/neato.\n");

void wrong_usage()
{
	std::cout << options.help() << std::endl;
	exit(1);
}

inline std::string neato_representation_with_weights(const Graph &G, std::vector<Number> &weakedges, std::vector<Number> &edges, std::string wl, std::string sl)
{
	std::stringstream ss;
	ss << "graph {\noverlap = false;\nsplines = false;\nsep=.3;\nnode[margin=0, fontsize=12, shape=circle, height=.3, width=.3];\n";
	for (auto ii : G.list(RP::all(), IP::primary())) {
		ss << ii->n1().to_int() << " -- " << ii->n2().to_int() << " [id=\"" << ii->l().index() << "\"";
        if (std::find(edges.begin(), edges.end(), ii->e().id().to_int()) != edges.end()) {
            ss << ", len=" << sl;
        } else if (std::find(weakedges.begin(), weakedges.end(), ii->e().id().to_int()) != weakedges.end()) {
            ss << ", len=" << wl;
        } 
        ss << "];\n";
    }
	ss << "}\n";
	return ss.str();
}


/*
CUT FORMAT:
n - num of graphs
n-times:
    m s - num of sets, smallest cut
    m-times:
        a b, c d, ... cut
*/


int main(int argc, char **argv) {
	try {
		options.add_options()
			("h, help", "print help")
			("i, input", "input file", cxxopts::value<std::string>()->default_value("graph"))
			;
		auto o = options.parse(argc, argv);

		if (o.count("help")) {
			std::cout << options.help() << std::endl;
			return 0;
		}

		std::string inputFileName = o["input"].as<std::string>();
		std::vector<Graph> graphs = std::move(read_oldBA_file(inputFileName));
        std::cout << graphs.size() << std::endl;
        for (int i = 0; i < graphs.size(); i++) {
            std::pair<int, std::set<std::set<Edge>>> cuts = cyclic_connectivity_with_cuts(graphs[i]);
            if (cuts.first == 0) {
                std::cout << "0 0" << std::endl;
                continue;
            }
            std::cout << cuts.second.size() << " " << cuts.first << std::endl;
            for (std::set<Edge> cut : cuts.second) {
                for (Edge edge : cut) {
                    std::cout << edge.v1().to_int() << " " << edge.v2().to_int() << ",  ";
                }
                std::cout << std::endl;
            }
        }
        /*
        auto rw = neato_representation_with_weights(G, weakchosen, vybrane, o["w"].as<std::string>(), o["s"].as<std::string>());
		if (o.count("dotprint"))
			std::cout << rw << std::endl;
        rw.erase(std::remove(rw.begin(), rw.end(), '\n'), rw.end());
        std::stringstream command;
        command << "echo '" << rw << "' | neato -Tpdf > '" << outputFileName << "'";

		if (system(command.str().c_str()) == -1)
			throw std::runtime_error("Call of system() failed");
		*/
	} catch (const cxxopts::OptionException& e) {
		std::cerr << "error parsing options: " << e.what() << std::endl;
		exit(1);
	}

	return 0;
}