## Gradle init-scripts

> The gradle `init-scripts` offer an alternative method to include
> runtime dependencies in your build without changing the main [build.grade](https://github.com/belgattitude/pjb-starter-springboot/blob/master/build.gradle) file.   


### Content

The [init-scripts folder](https://github.com/belgattitude/pjb-starter-springboot/tree/master/init-scripts) 
contains ready-made init-scripts for some popular libraries: 

| Type       | File | Description | 
|------------| ---- | ---------|
| PDF        | [./init.itext.gradle](https://github.com/belgattitude/pjb-starter-springboot/blob/master/init-scripts/init.itext.gradle) | [IText](http://itextpdf.com/) low level library to generate PDF |
| PDF        | [./init.pdfbox.gradle](https://github.com/belgattitude/pjb-starter-springboot/blob/master/init-scripts/init.pdfbox.gradle) | [PDFBox](http://pdfbox.apache.org/) Java tool for working with PDF documents. |
| DOC/XLS    | [./init.poi.gradle](https://github.com/belgattitude/pjb-starter-springboot/blob/master/init-scripts/init.poi.gradle) | [Apache POI](https://poi.apache.org/) Java API for Microsoft Documents. |
| REPORT     | [./init.jasperreports.gradle](https://github.com/belgattitude/pjb-starter-springboot/blob/master/init-scripts/init.jasperreports.gradle) | [JasperReports](http://community.jaspersoft.com/project/jasperreports-library) to generate PDF/XLS/HTML... reports |
| HTMLParser | [./init.jsoup.gradle](https://github.com/belgattitude/pjb-starter-springboot/blob/master/init-scripts/init.jsoup.gradle) | [Jsoup](https://github.com/jhy/jsoup/) Java HTML Parser, with best of DOM, CSS, and jquery |
| HTML2PDF   | [./init.flyingsaucer.gradle](https://github.com/belgattitude/pjb-starter-springboot/blob/master/init-scripts/init.flyingsaucer.gradle) | [FlyingSaucer](https://github.com/flyingsaucerproject/flyingsaucer) XML/XHTML and CSS 2.1 renderer in pure Java |
| JDBC       | [./init.mysql.gradle](https://github.com/belgattitude/pjb-starter-springboot/blob/master/init-scripts/init.mysql.gradle) | [MySQL JDBC connector](http://dev.mysql.com) |
| JDBC       | [./init.postgresql.gradle](https://github.com/belgattitude/pjb-starter-springboot/blob/master/init-scripts/init.postgresql.gradle) | [PostgreSQL JDBC connector](https://jdbc.postgresql.org/) |
| NLP        | [./init.opennlp.gradle](https://github.com/belgattitude/pjb-starter-springboot/blob/master/init-scripts/init.opennlp.gradle) | [OpenNLP](https://opennlp.apache.org/) natural language processing |
| NLP        | [./init.corenlp.gradle](https://github.com/belgattitude/pjb-starter-springboot/blob/master/init-scripts/init.corenlp.gradle) | [Standford CoreNLP](http://stanfordnlp.github.io/CoreNLP) - POSTagger, NER, ... |
| NLP        | [./init.corenlp-models.gradle](https://github.com/belgattitude/pjb-starter-springboot/blob/master/init-scripts/init.corenlp-models.gradle) | [Standford CoreNLP](http://stanfordnlp.github.io/CoreNLP) Trained core models - 400Mb |
| NLPModel   | [./init.corenlp-models-english.gradle](https://github.com/belgattitude/pjb-starter-springboot/blob/master/init-scripts/init.corenlp-models-english.gradle) | [Standford CoreNLP](http://stanfordnlp.github.io/CoreNLP) Trained models for English - 900Mb + 500M (KBP) |
| NLPModel   | [./init.corenlp-models-german.gradle](https://github.com/belgattitude/pjb-starter-springboot/blob/master/init-scripts/init.corenlp-models-german.gradle) | [Standford CoreNLP](http://stanfordnlp.github.io/CoreNLP) Trained models for German - 130Mb |
| NLPModel   | [./init.corenlp-models-french.gradle](https://github.com/belgattitude/pjb-starter-springboot/blob/master/init-scripts/init.corenlp-models-french.gradle) | [Standford CoreNLP](http://stanfordnlp.github.io/CoreNLP) Trained models for French - 130Mb |
| NLPModel   | [./init.corenlp-models-spanish.gradle](https://github.com/belgattitude/pjb-starter-springboot/blob/master/init-scripts/init.corenlp-models-spanish.gradle) | [Standford CoreNLP](http://stanfordnlp.github.io/CoreNLP) Trained models for Spanish - 200Mb |
| NLPModel   | [./init.corenlp-models-chinese.gradle](https://github.com/belgattitude/pjb-starter-springboot/blob/master/init-scripts/init.corenlp-models-chinese.gradle) | [Standford CoreNLP](http://stanfordnlp.github.io/CoreNLP) Trained models for Chinese - 800Mb |


> Those init-scripts can be used as example... You can use them to create your own dependencies requirements.

### Usage 

Add the `-I` (--init-script) parameter followed by the init script filename to the `./gradlew` command.

As an example, you can call the `war` task to generate a build with both `jasperreports` and `mysql-connector` like:

```shell
$ ./gradlew war -I init-scripts/init.jasperreports.gradle -I init-scripts/init.mysql.gradle
```

The newly builder war file present in the `build/libs` folder will now contains your dependencies
in its `WEB-INF/lib` internal directory.


