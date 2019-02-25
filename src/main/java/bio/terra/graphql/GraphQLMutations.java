package bio.terra.graphql;

import bio.terra.dao.StudyDao;
import bio.terra.metadata.Study;
import graphql.Scalars;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLInputObjectType;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import static graphql.Scalars.GraphQLString;
import static graphql.schema.GraphQLInputObjectField.newInputObjectField;

@Component
public class GraphQLMutations {

    @Autowired
    private StudyDao studyDao;

    GraphQLInputObjectType episodeType = GraphQLInputObjectType.newInputObject()
            .name("StudyInput")
            .field(newInputObjectField()
                    .name("name")
                    .type(GraphQLString))
            .build();

    GraphQLObjectType updateStudyMutation = newObject()
            .name("CreateReviewForEpisodeMutation")
            .field(newFieldDefinition()
                    .name("createReview")
                    .type(reviewType)
                    .argument(newArgument()
                            .name("episode")
                            .type(episodeType)
                    )
                    .argument(newArgument()
                            .name("review")
                            .type(reviewInputType)
                    )
                    .dataFetcher(mutationDataFetcher())
            )
            .build();

    GraphQLSchema schema = GraphQLSchema.newSchema()
            .query(queryType)
            .mutation(createReviewForEpisodeMutation)
            .build();    public DataFetcher setStudyName() {
        return new DataFetcher() {
            @Override
            public Study get(DataFetchingEnvironment environment) {
                //
                // The graphql specification dictates that input object arguments MUST
                // be maps.  You can convert them to POJOs inside the data fetcher if that
                // suits your code better
                //
                // See http://facebook.github.io/graphql/October2016/#sec-Input-Objects
                //
                Map<String, String> oldname = environment.getArgument("oldname");
                Map<String, String> newname = environment.getArgument("newname");

                //
                // in this case we have type safe Java objects to call our backing code with
                //

                System.out.println(oldname);
                System.out.println(newname);
                // make a call to your store to mutate your database
                Study study = studyDao.retrieveByName(oldname.getOrDefault("name", oldname.keySet().iterator().next()));

                study.setName("gothere");
                // this returns a new view of the data
                return study;
            }
        };
    }}
