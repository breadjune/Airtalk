package com.mobilepark.airtalk.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.lang.reflect.Method;
import java.util.*;

@Service
public class SpecificationService<T> {

    private static final Logger logger = LoggerFactory.getLogger(SpecificationService.class);


    enum Type {
        EQUALS, LIKE, ISEMPTY, ISNOTEMPTY, ISNOTNULL, ISNULL,OR
    }

    private Object invoke(T object, Method method) {

        Object obj = null;
        try {
            logger.debug("method => {}", method.getName());
            obj = method.invoke(object);

        } catch(Exception e) {
            logger.debug(e.getMessage());
        }
        return obj;
    }

    private Specification<T> specification(Type type, Set<String> set, T object) {

        return (Specification<T>) (root, query, builder) -> {

            List<Predicate> predicates = new ArrayList<>();

            Iterator<String> it = set.iterator();
            while(it.hasNext()) {
                String key = it.next();

                Class<T> clazz = (Class<T>) object.getClass();
                Method[] methods = clazz.getMethods();

                for(Method method: methods) {
                    String methodName = "get" + key.substring(0,1).toUpperCase() + key.substring(1);

                    if (StringUtils.equals(method.getName(), methodName)) {
                        Object value;
                        value = this.invoke(object, method);
                        if (value == null) continue;

                        logger.debug("----------------------------- {}", value);

                        switch (type) {
                            case EQUALS:
                                predicates.add(builder.equal(root.get(key), value));
                                break;
                            case LIKE:
                                predicates.add(builder.like(root.get(key), "%" + value + "%"));
                                break;
                            case ISEMPTY:
                                predicates.add(builder.isEmpty(root.get(key)));
                                break;
                            case ISNOTEMPTY:
                                predicates.add(builder.isNotEmpty(root.get(key)));
                                break;
                            case ISNULL:
                                predicates.add(builder.isNull(root.get(key)));
                                break;
                            case ISNOTNULL:
                                predicates.add(builder.isNotNull(root.get(key)));
                                break;
                            case OR: { //코드값 1글자 체크를 위해서
                                if (value.toString().length() ==1)
                                    predicates.add(builder.equal(root.get(key), value));
                                else
                                    predicates.add(builder.like(root.get(key), "%" + value + "%"));
                                break;
                            }
                        }
                    }
                }
            }

            if (type.toString().equals("OR"))//전체 검색할시 or 조건으로 검색
                return builder.or(predicates.toArray(new Predicate[0]));
            else
                return builder.and(predicates.toArray(new Predicate[0]));
        };
    }


    public Specification<T> isEmpty(Set<String> set, T object){
        return this.specification(Type.ISEMPTY, set, object);
    }

    public Specification<T> isNotEmpty(Set<String> set, T object){
        return this.specification(Type.ISNOTEMPTY, set, object);
    }

    public Specification<T> isNull(Set<String> set, T object){
        return this.specification(Type.ISNULL, set, object);
    }

    public Specification<T> isNotNull(Set<String> set, T object){
        return this.specification(Type.ISNOTNULL, set, object);
    }

    public Specification<T> equals(Set<String> set, T object){
        return this.specification(Type.EQUALS, set, object);
    }

    public Specification<T> like(Set<String> set, T object) {
        return this.specification(Type.LIKE, set, object);
    }

    public Specification<T> between(String column, Date start, Date end) {
        return (Specification<T>) (root, query, builder) -> builder.between(root.get(column), start, end);
    }
    public Specification<T> or(Set<String> set, T object) {
        return this.specification(Type.OR, set, object);
    }

    public Specification<T> equals(String column, String date, String pattern) throws Exception{

        Date start = DateUtils.parseDate(date, pattern);
        Date end = DateUtils.addDays(start, 1);

        return this.between(column, start, end);
    }
}
