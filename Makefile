repl:
	bb nrepl

uberjar:
	bb uberjar artifacts/way.jar -m way.main

exe: uberjar
	cat ~/bin/bb artifacts/way.jar > artifacts/way

deploy: exe
	cp artifacts/way ~/bin/
