package bio.terra.dao;

import bio.terra.metadata.StudyRelationship;
import bio.terra.metadata.Study;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class RelationshipDao extends MetaDao<StudyRelationship> {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public RelationshipDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // part of a transaction propagated from StudyDao
    public void createStudyRelationships(Study study) {
        for (StudyRelationship rel : study.getRelationships()) {
            create(rel);
        }
    }

    protected void create(StudyRelationship studyRelationship) {
        String sql = "INSERT INTO study_relationship " +
                "(name, from_cardinality, to_cardinality, from_column, to_column) VALUES " +
                "(:name, :from_cardinality, :to_cardinality, :from_column, :to_column)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", studyRelationship.getName());
        params.addValue("from_cardinality", studyRelationship.getFromCardinality().toString());
        params.addValue("to_cardinality", studyRelationship.getToCardinality().toString());
        params.addValue("from_column", studyRelationship.getFrom().getId());
        params.addValue("to_column", studyRelationship.getTo().getId());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, params, keyHolder);
        UUID relationshipId = getIdKey(keyHolder);
        studyRelationship.setId(relationshipId);
    }

}
