CFLAGS = -std=c++17 -fconcepts -I../../include
LDFLAGS =
DBGFLAGS = -g -O0 -pedantic -Wall -Wextra -DBA_GRAPH_DEBUG -D_GLIBCXX_DEBUG
COMPILE_DBG = $(CXX) $(CFLAGS) $(DBGFLAGS)
COMPILE = $(CXX) $(CFLAGS) -DNDEBUG -O3

all: showcutgraph


showcutgraph:
	$(COMPILE) showcutgraph.cpp -o showcutgraph $(LDFLAGS)

showcutgraph_dbg:
	$(COMPILE_DBG) showcutgraph.cpp -o showcutgraph $(LDFLAGS)


clean:
	rm -rf *.out


.PHONY: clean all showcutgraph showcutgraph_dbg
