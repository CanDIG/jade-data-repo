package bio.terra.graphql;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

@Component
public class GraphQLProvider {

    private GraphQL graphQL;

    @Bean
    public GraphQL graphQL() {
        return graphQL;
    }

    @PostConstruct
    public void init() throws IOException {
        URL url = Resources.getResource("schema.graphqls");
        String sdl = Resources.toString(url, Charsets.UTF_8);
        GraphQLSchema graphQLSchema = buildSchema(sdl);
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }

    @Autowired
    GraphQLDataFetchers graphQLDataFetchers;
    @Autowired
    GraphQLMutations graphQLMutations;

    private GraphQLSchema buildSchema(String sdl) {
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }

    private RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type(newTypeWiring("Query")
                        .dataFetcher("studyByName", graphQLDataFetchers.getStudyByNameDataFetcher()))
                .type(newTypeWiring("Query")
                        .dataFetcher("studies", graphQLDataFetchers.getStudiesDataFetcher()))
                .type(newTypeWiring("Study")
                        .dataFetcher("id", graphQLDataFetchers.getStudyIdDataFetcher()))
                .type(newTypeWiring("Study")
                        .dataFetcher("tables", graphQLDataFetchers.getStudyTablesDataFetcher()))
                .type(newTypeWiring("Study")
                        .dataFetcher("relationships", graphQLDataFetchers.getStudyRelationshipsDataFetcher()))
                .type(newTypeWiring("RelationshipModel")
                        .dataFetcher("fromTable", graphQLDataFetchers.getRelationshipFromTableDataFetcher()))
                .type(newTypeWiring("RelationshipModel")
                        .dataFetcher("fromColumn", graphQLDataFetchers.getRelationshipFromColumnDataFetcher()))
                .type(newTypeWiring("RelationshipModel")
                        .dataFetcher("toTable", graphQLDataFetchers.getRelationshipToTableDataFetcher()))
                .type(newTypeWiring("RelationshipModel")
                        .dataFetcher("toColumn", graphQLDataFetchers.getRelationshipToColumnDataFetcher()))
//                .type(newTypeWiring("Table")
//                        .dataFetcher("name", graphQLDataFetchers.getStudyTableNameDataFetcher()))
                .type(newTypeWiring("Mutation")
                        .dataFetcher("name", graphQLMutations.setStudyName()))
                .build();
    }
}