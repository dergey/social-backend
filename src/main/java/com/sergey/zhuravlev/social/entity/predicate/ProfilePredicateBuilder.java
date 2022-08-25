package com.sergey.zhuravlev.social.entity.predicate;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.sergey.zhuravlev.social.entity.QProfile;
import com.sergey.zhuravlev.social.enums.RelationshipStatus;

import java.time.LocalDate;

public class ProfilePredicateBuilder {

    private static final QProfile profile = new QProfile("profile");

    private BooleanExpression cityExpression;
    private BooleanExpression countryExpression;
    private BooleanExpression ageExpression;
    private BooleanExpression queryExpression;

    public ProfilePredicateBuilder withCountry(String country) {
        if (country != null && !country.isEmpty()) {
            countryExpression = profile.address.country.eq(country);
        }
        return this;
    }

    public ProfilePredicateBuilder withCity(String city) {
        if (city != null && !city.isEmpty()) {
            cityExpression = profile.address.city.eq(city);
        }
        return this;
    }

    public ProfilePredicateBuilder withRelationshipStatus(RelationshipStatus relationshipStatus) {
        if (relationshipStatus != null) {
            cityExpression = profile.relationshipStatus.eq(relationshipStatus);
        }
        return this;
    }

    public ProfilePredicateBuilder withAge(Integer age) {
        if (age != null) {
            ageExpression = profile.birthDate
                    .between(LocalDate.now().minusYears(age + 1), LocalDate.now().minusYears(age));
        }
        return this;
    }

    public ProfilePredicateBuilder withAgeBetween(Integer fromAge, Integer toAge) {
        if (fromAge != null && toAge != null) {
            ageExpression = profile.birthDate
                    .between(LocalDate.now().minusYears(toAge + 1), LocalDate.now().minusYears(fromAge));
        } else if (fromAge != null) {
            ageExpression = profile.birthDate
                    .before(LocalDate.now().minusYears(fromAge));
        } else if (toAge != null) {
            ageExpression = profile.birthDate
                    .after(LocalDate.now().minusYears(toAge + 1));
        }
        return this;
    }

    public ProfilePredicateBuilder withQuery(String query) {
        if (query != null && !query.isEmpty()) {
            queryExpression = profile.searchString.like("%" + query.toUpperCase() + "%");
        }
        return this;
    }

    public Predicate build() {
        Predicate predicate = ExpressionUtils.allOf(countryExpression, cityExpression, ageExpression, queryExpression);
        return predicate == null ? Expressions.TRUE.isTrue() : predicate;
    }

}
