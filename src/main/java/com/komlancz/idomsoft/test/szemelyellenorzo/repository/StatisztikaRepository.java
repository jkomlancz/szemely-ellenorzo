package com.komlancz.idomsoft.test.szemelyellenorzo.repository;

import com.komlancz.idomsoft.test.szemelyellenorzo.model.Statisztika;

import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;

@Repository public interface StatisztikaRepository extends CrudRepository<Statisztika, Long>
{
    Statisztika getOneByFunkcioNev(String funkcioNev);
}
