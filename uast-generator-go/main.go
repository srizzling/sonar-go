// Command line interface to uast-generator-go
package main

import (
	"flag"
	"fmt"
	"os"
)

func exit() {
	flag.Usage()
	os.Exit(1)
}

type Params struct {
	dumpAst bool
	path    string
}

func parseArgs() Params {
	flag.Usage = func() {
		fmt.Printf("Usage: %s [options] source.go\n\n", os.Args[0])
		flag.PrintDefaults()
	}

	dumpAstFlag := flag.Bool("d", false, "dump ast (instead of JSON)")
	flag.Parse()

	if len(flag.Args()) != 1 {
		exit()
	}

	return Params{
		dumpAst: *dumpAstFlag,
		path:    flag.Args()[0],
	}
}

func main() {
	params := parseArgs()

	fileSet, astFile, fileContent, err := readAstFile(params.path)
	if err != nil {
		panic(err)
	}

	if params.dumpAst {
		fmt.Println(render(astFile))
	} else {
		uast := toUast(fileSet, astFile, fileContent)
		PrintJson(uast)
	}
}
