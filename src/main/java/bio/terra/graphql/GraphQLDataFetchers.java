package bio.terra.graphql;

import bio.terra.dao.StudyDao;
import bio.terra.metadata.Study;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GraphQLDataFetchers {

    @Autowired
    private StudyDao studyDao;

    public DataFetcher getStudyByNameDataFetcher() {
        return dataFetchingEnvironment -> {
        String studyName = dataFetchingEnvironment.getArgument("name");
        return studyDao.retrieveByName(studyName);
    };
    }

    public DataFetcher getIdDataFetcher() {
        return dataFetchingEnvironment -> {
            Study study = dataFetchingEnvironment.getSource();
            return study.getId().toString();
        };
    }
}
