package com.cognizant.ormlearn.service.impl;

import com.cognizant.ormlearn.model.Product;
import com.cognizant.ormlearn.model.ProductFilterRequest;
import com.cognizant.ormlearn.service.ProductService;

import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Product> getFilteredProducts(ProductFilterRequest filterRequest) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> product = cq.from(Product.class);

        List<Predicate> predicates = new ArrayList<>();

        if (filterRequest.getMinCpuSpeed() != null)
            predicates.add(cb.greaterThanOrEqualTo(product.get("cpuSpeed"), filterRequest.getMinCpuSpeed()));

        if (filterRequest.getMinRamSize() != null)
            predicates.add(cb.greaterThanOrEqualTo(product.get("ramSize"), filterRequest.getMinRamSize()));

        if (filterRequest.getMinHddSize() != null)
            predicates.add(cb.greaterThanOrEqualTo(product.get("hddSize"), filterRequest.getMinHddSize()));

        if (filterRequest.getOperatingSystem() != null)
            predicates.add(cb.equal(product.get("operatingSystem"), filterRequest.getOperatingSystem()));

        if (filterRequest.getMaxWeight() != null)
            predicates.add(cb.lessThanOrEqualTo(product.get("weight"), filterRequest.getMaxWeight()));

        if (filterRequest.getCpu() != null)
            predicates.add(cb.equal(product.get("cpu"), filterRequest.getCpu()));

        if (filterRequest.getMinReview() != null)
            predicates.add(cb.greaterThanOrEqualTo(product.get("customerReview"), filterRequest.getMinReview()));

        cq.where(cb.and(predicates.toArray(new Predicate[0])));

        return em.createQuery(cq).getResultList();
    }
}
