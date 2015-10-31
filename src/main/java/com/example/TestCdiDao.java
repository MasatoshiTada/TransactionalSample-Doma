package com.example;

import org.seasar.doma.*;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by tada on 2015/10/31.
 */
@Dao
@AnnotateWith(annotations = {
        @Annotation(target = AnnotationTarget.CLASS, type = Dependent.class),
        @Annotation(target = AnnotationTarget.CONSTRUCTOR, type = Inject.class)
})
public interface TestCdiDao {

    @Script
    void dropAndCreate();

    @Select
    List<TestEntity> selectAll();

    @Insert
    int insert(TestEntity testEntity);
}
