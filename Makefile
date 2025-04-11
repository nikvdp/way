repl:
	bb nrepl

uberjar:
	bb uberjar artifacts/way.jar -m way.main

exe: uberjar
	cat ~/bin/bb artifacts/way.jar > artifacts/way

deploy: uberjar
	cp artifacts/way ~/bin/
