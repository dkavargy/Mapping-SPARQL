import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.http.HTTPRepository;
import org.eclipse.rdf4j.rio.RDFFormat;

import java.io.IOException;

public class GraphDBExample {

    /**
     * How to connect to a graphdb repository, load a file to a repository, add some data through the API and perform a
     * sparql query
     */
    public static void main(String[] args) {

        HTTPRepository repository = new HTTPRepository("http://localhost:7200/repositories/myIMDb");
        RepositoryConnection connection = repository.getConnection();

        // Clear the repository before we start
        connection.clear();

        // load a simple ontology from a file
        connection.begin();
        // Adding the family ontology
        try {
            connection.add(GraphDBExample.class.getResourceAsStream("./src/main/java/OutPut_overall.ttl"), "urn:base",
                    RDFFormat.TURTLE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Committing the transaction persists the data
        connection.commit();

        ModelBuilder builder = new ModelBuilder();
        builder.setNamespace("rat", "http://www.semanticweb.org/30693/ontologies/2022/5/untitled-ontology-26")
                .subject("rat:ratings")
                .add(RDF.TYPE, "rdfs:label");

        Model model = builder.build();

        // add our data
        connection.begin();
        connection.add(model);
        connection.commit();

        // We do a simple SPARQL SELECT-query that retrieves all resources of type `ex:Artist`,
        // and their first names.
        String queryString = "PREFIX domain: <http://www.semanticweb.org/30693/ontologies/2022/5/untitled-ontology-26> \n";
        queryString += "SELECT  (MAX (?val) AS ?max_ratings) ?actor_name  \n";
        queryString += "WHERE { \n";
        queryString += " ?x act:primaryName ?actor_name.\n" +
                "        ?d act:KnownForTitles ?Movies_actor."+
                "        ?s rat:total_ratings  ?val. "+
                "        ?ratings rat:total_ratings ?total_ratings."+
                "        filter (?d/?s)";
        queryString += "group by ?name";
        queryString += "}";

        TupleQuery query = connection.prepareTupleQuery(queryString);

        // A QueryResult is also an AutoCloseable resource, so make sure it gets closed when done.
        try (TupleQueryResult result = query.evaluate()) {
            // we just iterate over all solutions in the result...
            for (BindingSet solution : result) {
                // ... and print out the value of the variable binding for ?s and ?n
                System.out.println("?actor_name = " + solution.getValue("name"));
            }
        }

        connection.close();
        repository.shutDown();
    }
}
