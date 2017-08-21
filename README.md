# pdf-unstamper
Text stamp remover for PDF files.

Powered by [Apache PDFBox®](https://pdfbox.apache.org/).

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
</tbody>
</table>

## Download
Download [JAR](https://github.com/hwding/pdf-unstamper/releases).

## Help
```
Usage: 
   [OPTION] -i [INPUT PDF] -k [KEYWORDS] (-o [OUTPUT PDF])
   [OPTION] -I [INPUT DIR] -k [KEYWORDS] (-O [OUTPUT DIR])

Options:
   -d,  --directly          directly modify the input file(s), which makes option o/O unnecessary
   -r,  --recursive         process files in the given dir recursively
```

## Example
- For single file

  ```
  ➜ java -jar pdf-unstamper.jar -i PythonRequestsEssentials.pdf -o PythonRequestsEssentials.unstamped.pdf -k www.allitebooks.com
  ```
  Or
  ```
  ➜ java -jar pdf-unstamper.jar -i PythonRequestsEssentials.pdf -d -k www.allitebooks.com
  ```
- For massive files

  ```
  ➜ java -jar pdf-unstamper.jar -I pdfs/ -O unstampedPdfs/ -r -k www.allitebooks.com
  ```
  Or
  ```
  ➜ java -jar pdf-unstamper.jar -I pdfs/ -d -r -k www.allitebooks.com
  ```
  
