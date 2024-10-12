package org.project.onboarding.predicates.TeamMember;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.project.onboarding.exception.BusinessException;
import org.project.onboarding.predicates.SearchCriteria;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
public class TeamMemberPredicateBuilder {
    private List<SearchCriteria> params;

    public TeamMemberPredicateBuilder() {
        params = new ArrayList<>();
    }

    public TeamMemberPredicateBuilder with(
            String key, String operation, Object value) {

        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public BooleanExpression build() {
        if (params.isEmpty()) {
            return null;
        }

        List<BooleanExpression> predicates = params.stream().map(param -> {
            TeamMemberPredicate predicate = new TeamMemberPredicate(param);
            try {
                return predicate.getPredicate();
            } catch (BusinessException e) {
                throw new RuntimeException(e);
            }
        }).filter(Objects::nonNull).toList();

        BooleanExpression result = Expressions.asBoolean(true).isTrue();
        for (BooleanExpression predicate : predicates) {
            result = result.and(predicate);
        }
        return result;
    }
}
