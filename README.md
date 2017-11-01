# pdf-unstamper
![](https://img.shields.io/badge/Powered%20by-Apache%20PDFBox-blue.svg?style=flat-square)

Remove text stamps of **any font**, **any encoding** and **any language** with pdf-unstamper now!

## Effect
<table>
<thead>
<tr>
<th>Before</th>
<th>After</th>
</tr>
</thead>
<tbody>
<tr>
<td><img src="https://github.com/hwding/pdf-unstamper/blob/master/art/before.png"></td>
<td><img src="https://github.com/hwding/pdf-unstamper/blob/master/art/after.png"></td>
</tr>
<tr>
<td><img src="https://github.com/hwding/pdf-unstamper/blob/master/art/before-ituring.png"></td>
<td><img src="https://github.com/hwding/pdf-unstamper/blob/master/art/after-ituring.png"></td>
</tr>
</tbody>
</table>

## Usage
```
Usage:
   [OPTION] -i [INPUT PDF] -k [KEYWORDS...] (-o [OUTPUT PDF])
   [OPTION] -I [INPUT DIR] -k [KEYWORDS...] (-O [OUTPUT DIR])

Options:
   -d,  --directly          directly modify the input file(s), option o/O is
                            unnecessary when this option is on
   -r,  --recursive         process files in the given dir recursively
   -s,  --strict            use strict mode, a text area is considered as water mark
                            only if its content strictly equals one of the keywords
```

## Get it now
### As a command
Install (or update to) the latest version as command `unstamp` in `~/bin/` and soft-linked as `/usr/local/bin/unstamp` for convenience.
#### Install
Make sure you have `wget` installed.
```shell
➜ sudo bash -c "$(curl -s https://raw.githubusercontent.com/hwding/pdf-unstamper/master/script/install)"

# If using a proxy
➜ sudo proxychains bash -c "$(curl -s https://raw.githubusercontent.com/hwding/pdf-unstamper/master/script/install)"

# Script options
#
# -d [DIR]  install binary into a specified directory
# -w        install without creating a soft-link to /usr/local/bin/

# If install with options, download the script first
➜ wget https://raw.githubusercontent.com/hwding/pdf-unstamper/master/script/install

# Then execute with or without proxy
➜ sudo bash install -d ~/my-bins/
➜ sudo bash install -w
➜ sudo bash install -d ~/my-bins/ -w
➜ sudo proxychains bash install -d ~/my-bins/
➜ sudo proxychains bash install -w
➜ sudo proxychains bash install -d ~/my-bins/ -w
```
#### Run
```shell
# For single file processing
➜ unstamp -i "C Recipes.pdf" -o "C Recipes.unstamped.pdf" -k www.allitebooks.com -s
➜ unstamp -i RoR.pdf -o RoR.unstamped.pdf -k 图灵社区会员
# Or
➜ unstamp -i "C Recipes.pdf" -d -k www.allitebooks.com -s
➜ unstamp -i RoR.pdf -d -k 图灵社区会员
 
# For massive files processing
➜ unstamp -I pdfs/ -O unstampedPdfs/ -r -k 图灵社区会员 www.allitebooks.com
# Or
➜ unstamp -I pdfs/ -d -r -k 图灵社区会员 www.allitebooks.com
```

### As a JAR
#### Download
Get [*pdf-unstamper.jar*](https://github.com/hwding/pdf-unstamper/releases).
#### Run
```shell
# For single file processing
➜ java -jar pdf-unstamper.jar -i "C Recipes.pdf" -o "C Recipes.unstamped.pdf" -k www.allitebooks.com -s
➜ java -jar pdf-unstamper.jar -i RoR.pdf -o RoR.unstamped.pdf -k 图灵社区会员
# Or
➜ java -jar pdf-unstamper.jar -i "C Recipes.pdf" -d -k www.allitebooks.com -s
➜ java -jar pdf-unstamper.jar -i RoR.pdf -d -k 图灵社区会员
 
# For massive files processing
➜ java -jar pdf-unstamper.jar -I pdfs/ -O unstampedPdfs/ -r -k 图灵社区会员 www.allitebooks.com
# Or
➜ java -jar pdf-unstamper.jar -I pdfs/ -d -r -k 图灵社区会员 www.allitebooks.com
```
## Structure
```
src/com/amastigote/unstamper
├── core
│   ├── Processor.java
│   └── TextStampRecognizer.java
├── io
│   └── IOHandler.java
├── log
│   └── GeneralLogger.java
├── Main.java
└── util
    ├── OptionManager.java
    └── TaskRunner.java
```
