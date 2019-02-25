package bio.terra.graphql;

import bio.terra.dao.StudyDao;
import bio.terra.metadata.Study;
import bio.terra.metadata.StudyRelationship;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

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

    public DataFetcher getStudiesDataFetcher() {
        return dataFetchingEnvironment -> {
            return studyDao.enumerate().stream().map(summary -> (Study) summary).collect(Collectors.toList());
        };
    }

    public DataFetcher getStudyIdDataFetcher() {
        return dataFetchingEnvironment -> {
            Study study = dataFetchingEnvironment.getSource();
            return study.getId().toString();
        };
    }

    public DataFetcher getStudyTablesDataFetcher() {
        return dataFetchingEnvironment -> {
            Study study = dataFetchingEnvironment.getSource();
            return study.getTables();
        };
    }

    public DataFetcher getStudyRelationshipsDataFetcher() {
        return dataFetchingEnvironment -> {
            Study study = dataFetchingEnvironment.getSource();
            return study.getRelationships();
        };
    }

//    public DataFetcher getStudyTableNameDataFetcher() {
//        return dataFetchingEnvironment -> {
//            StudyTable table = dataFetchingEnvironment.getSource();
//            return table.getName();
//        };
//    }

    public DataFetcher getRelationshipFromTableDataFetcher() {
        return dataFetchingEnvironment -> {
            StudyRelationship rel = dataFetchingEnvironment.getSource();
            return rel.getFrom().getInTable().getName();
        };
    }

    public DataFetcher getRelationshipFromColumnDataFetcher() {
        return dataFetchingEnvironment -> {
            StudyRelationship rel = dataFetchingEnvironment.getSource();
            return rel.getFrom().getName();
        };
    }

    public DataFetcher getRelationshipToTableDataFetcher() {
        return dataFetchingEnvironment -> {
            StudyRelationship rel = dataFetchingEnvironment.getSource();
            return rel.getTo().getInTable().getName();
        };
    }

    public DataFetcher getRelationshipToColumnDataFetcher() {
        return dataFetchingEnvironment -> {
            StudyRelationship rel = dataFetchingEnvironment.getSource();
            return rel.getTo().getName();
        };
    }

}
