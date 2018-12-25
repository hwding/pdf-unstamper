# pdf-unstamper
![](https://img.shields.io/badge/Powered%20by-Apache%20PdfBox-green.svg?style=flat-square)  
Remove textual watermark of **any font**, **any encoding** and **any language** with pdf-unstamper now!

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
<td><img height="75" src="https://github.com/hwding/pdf-unstamper/blob/master/art/before-frame.png"></td>
<td><img height="75" src="https://github.com/hwding/pdf-unstamper/blob/master/art/after-frame.png"></td>
</tr>
<tr>
<td><img src="https://github.com/hwding/pdf-unstamper/blob/master/art/before-ituring.png"></td>
<td><img src="https://github.com/hwding/pdf-unstamper/blob/master/art/after-ituring.png"></td>
</tr>
<tr>
<td><img width="230" src="https://github.com/hwding/pdf-unstamper/blob/master/art/before_stringarray.png"></td>
<td><img width="230" src="https://github.com/hwding/pdf-unstamper/blob/master/art/after_stringarray.png"></td>
</tr>
</tbody>
</table>

## Usage
```
Usage:
   [OPTION] -i [INPUT PDF] -k [KEYWORDS...] (-o [OUTPUT PDF])
   [OPTION] -I [INPUT DIR] -k [KEYWORDS...] (-O [OUTPUT DIR])

Options:
   -d,  --direct            directly modify the input file(s), option o/O is
                            unnecessary when this option is on
   -r,  --recursive         process files in the given dir recursively
   -s,  --strict            use strict mode, a text area is considered as watermark
                            only if its content strictly equals one of the keywords
   -c,  --clear             clear all annotations in pages which contains the target
                            textual watermark(s), if you encounter bordered frame issues,
                            enable this switch
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
➜ unstamp -i "C Recipes.pdf" -o "C Recipes.unstamped.pdf" -k www.allitebooks.com -s -c
➜ unstamp -i RoR.pdf -o RoR.unstamped.pdf -k 图灵社区会员
# Or
➜ unstamp -i "C Recipes.pdf" -d -k www.allitebooks.com -s -c
➜ unstamp -i RoR.pdf -d -k 图灵社区会员
 
# For massive files processing
➜ unstamp -I pdfs/ -O unstampedPdfs/ -r -k 图灵社区会员 www.allitebooks.com -c
# Or
➜ unstamp -I pdfs/ -d -r -k 图灵社区会员 www.allitebooks.com -c
```

### As a JAR
#### Download
Get [*pdf-unstamper.jar*](https://github.com/hwding/pdf-unstamper/releases).
#### Run
```shell
# For single file processing
➜ java -jar pdf-unstamper.jar -i "C Recipes.pdf" -o "C Recipes.unstamped.pdf" -k www.allitebooks.com -s -c
➜ java -jar pdf-unstamper.jar -i RoR.pdf -o RoR.unstamped.pdf -k 图灵社区会员
# Or
➜ java -jar pdf-unstamper.jar -i "C Recipes.pdf" -d -k www.allitebooks.com -s -c
➜ java -jar pdf-unstamper.jar -i RoR.pdf -d -k 图灵社区会员
 
# For massive files processing
➜ java -jar pdf-unstamper.jar -I pdfs/ -O unstampedPdfs/ -r -k 图灵社区会员 www.allitebooks.com -c
# Or
➜ java -jar pdf-unstamper.jar -I pdfs/ -d -r -k 图灵社区会员 www.allitebooks.com -c
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
