package com.codegym.jira.ref.internal;

import com.codegym.jira.ref.RefType;
import com.codegym.jira.common.BaseRepository;
import com.codegym.jira.common.error.NotFoundException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface ReferenceRepository extends BaseRepository<Reference> {
    List<Reference> findAllByOrderByIdAsc();

    @Query("SELECT r FROM Reference r WHERE r.refType=:type")
    List<Reference> getByType(RefType type);

    @Query("SELECT r FROM Reference r WHERE r.refType=:type AND r.code = :code")
    Optional<Reference> getByTypeAndCode(RefType type, String code);

    default Reference getExistedByTypeAndCode(RefType type, String code) {
        return getByTypeAndCode(type, code).orElseThrow(() -> new NotFoundException("Ref with type=" + type + ", code=" + code + " not found"));
    }
}
