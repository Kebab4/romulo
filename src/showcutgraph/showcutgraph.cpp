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

void missingError()
{
	fprintf(stderr, "There is no such graph in the input file\n");
	exit(1);
}

Graph getGraphStream(std::istream &in, int graphIndex)
{
	auto graphs = read_graph6_stream(in, static_factory, graphIndex, graphIndex + 1);
	if (graphs.size() == 0)
		missingError();
	return std::move(graphs[0]);
}

Graph getGraphFile(std::string fileFormat, std::string inputFileName, int graphIndex)
{
	Graph G = createG();
	if (fileFormat == "g6" || fileFormat == "s6") {
		auto graphs = read_graph6_file(inputFileName, static_factory, graphIndex, graphIndex + 1).graphs();
		if (graphs.size() == 0)
			missingError();
		G = std::move(graphs[0]);
	} else if (fileFormat == "ba") {
		auto graphs = read_oldBA_file(inputFileName);
		if (unsigned(graphIndex) >= graphs.size())
			missingError();
		G = std::move(graphs[graphIndex]);
	} else {
		wrong_usage();
	}
	return G;
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

int main(int argc, char **argv) {
	try {
		options.add_options()
			("h, help", "print help")
			("f, format", "format (g6/s6/ba)", cxxopts::value<std::string>()->default_value("g6"))
			("o, output", "output file", cxxopts::value<std::string>()->default_value("showcutgraph.pdf"))
			("i, input", "input file", cxxopts::value<std::string>())
			("n, index", "graph index", cxxopts::value<int>()->default_value(std::to_string(0)))
			("w, weaklen", "weak length", cxxopts::value<std::string>()->default_value("1.3"))
			("s, stronglen", "strong length", cxxopts::value<std::string>()->default_value("1.8"))
			("p, dotprint", "print dot file")
			("d, debug", "debug mode")
			;
		auto o = options.parse(argc, argv);

		if (o.count("help")) {
			std::cout << options.help() << std::endl;
			return 0;
		}

		std::string fileFormat = o["format"].as<std::string>();
		std::string outputFileName = o["output"].as<std::string>();
		int graphIndex = o["index"].as<int>();

		Graph G = createG();
		std::string inputFileName;
		if (o.count("input")) {
			inputFileName = o["input"].as<std::string>();
			G = std::move(getGraphFile(fileFormat, inputFileName, graphIndex));
		} else {
			G = std::move(getGraphStream(std::cin, graphIndex));
		}

        std::pair<int, std::set<std::set<Edge>>> cuts = cyclic_connectivity_with_cuts(G);
		if (o.count("debug"))
        	std::cout << inputFileName << " : size " << cuts.first << " cuts " << cuts.second.size() << std::endl;
        std::vector<Number> weakchosen = {};
        std::vector<Number> vybrane;
        for (std::set<Edge> cut : cuts.second) {
            vybrane = {};
			if (o.count("debug"))
				std::cout << "cut detail: ";
            for (Edge edge : cut) {
				if (o.count("debug"))
					std::cout << edge.v1().to_int() << " " << edge.v2().to_int() << ",  ";
                weakchosen.push_back(Number(edge.to_int()));
                vybrane.push_back(Number(edge.to_int()));
            }
			if (o.count("debug"))
				std::cout << std::endl;
        }
        auto rw = neato_representation_with_weights(G, weakchosen, vybrane, o["w"].as<std::string>(), o["s"].as<std::string>());
		if (o.count("dotprint"))
			std::cout << rw << std::endl;
        rw.erase(std::remove(rw.begin(), rw.end(), '\n'), rw.end());
        std::stringstream command;
        command << "echo '" << rw << "' | neato -Tpdf > '" << outputFileName << "'";

		if (system(command.str().c_str()) == -1)
			throw std::runtime_error("Call of system() failed");
	} catch (const cxxopts::OptionException& e) {
		std::cerr << "error parsing options: " << e.what() << std::endl;
		exit(1);
	}

	return 0;
}