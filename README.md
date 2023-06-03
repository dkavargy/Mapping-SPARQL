# SPARQL query integration - Ontology

## 📝 Description
The purpose of this project is to implement a Java program for converting and storing a dataset from IMDb into an RDF graph. The data is available on e-learning and includes:
The program will utilize RML (RDF Mapping Language) to define the mapping rules between the input data and the RDF graph representation. The converted RDF data will be stored in a GraphDB and managed using RDF4J, a Java library for working with RDF data. This project aims to facilitate semantic querying and analysis of IMDb data by transforming it into a linked data format and leveraging the capabilities of GraphDB and RDF4J.

## 📚 Dataset

  * actors.json
  * movies.tsv
  * ratings.tsv

## 🖥️ Installation

### ⚙️ Setup

### start GraphDB:

    Download the GraphDB distribution package from the official website (e.g., graphdb-free-9.0.0-dist.zip).
    Unzip the package to a desired location on your system.
    Open a terminal or command prompt and navigate to the GraphDB installation directory.

### start GraphDB:

  *  Execute the following command to start GraphDB:
    
```bash
./bin/graphdb

```
    
  *  GraphDB will start running and can be accessed through a web browser at ** http://localhost:7200.**

### 🛠️ Requirements

 - Transformation of movies.tsv and ratings.tsv into .csv file for the purposes of the project.
 - Open myImdb ontology to see the relationships between clases and object properties
 - run RML_Rules_Mapping.java to export the rml_overall.ttl file that includes rml rules for all the above files
 - Open the repository that just created in the GraphDb with tha name: myIMDb
 - run RML_Pipeline.java to export graph based in the created ontology
 - run GrapgDBExample.java to load the previous graph and to run the SPARQL queries



