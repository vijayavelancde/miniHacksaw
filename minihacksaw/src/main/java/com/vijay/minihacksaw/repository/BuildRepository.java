package com.vijay.minihacksaw.repository;

import java.util.*;
import org.springframework.stereotype.Repository;
import com.vijay.minihacksaw.model.Build;

@Repository
public class BuildRepository {

    private final List<Build> builds = new ArrayList<>();

    public void save(Build build) {
        builds.add(build);
    }

    public List<Build> findByProductId(String productId) {
        List<Build> result = new ArrayList<>();
        for (Build b : builds) {
            if (b.getProductId().equals(productId)) {
                result.add(b);
            }
        }
        return result;
    }

    public List<Build> findAll() {
        return builds;
    }
}