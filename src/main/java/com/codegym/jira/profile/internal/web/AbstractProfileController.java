package com.codegym.jira.profile.internal.web;

import com.codegym.jira.common.util.validation.ValidationUtil;
import com.codegym.jira.profile.ProfileTo;
import com.codegym.jira.profile.internal.ProfileMapper;
import com.codegym.jira.profile.internal.ProfileRepository;
import com.codegym.jira.profile.internal.ProfileUtil;
import com.codegym.jira.profile.internal.model.Profile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public abstract class AbstractProfileController {
    @Autowired
    protected ProfileMapper profileMapper;
    @Autowired
    private ProfileRepository profileRepository;

    public ProfileTo get(long id) {
        log.info("get {}", id);
        return profileMapper.toTo(profileRepository.getOrCreate(id));
    }

    public void update(ProfileTo profileTo, long id) {
        log.info("update {}, user {}", profileTo, id);
        ValidationUtil.assureIdConsistent(profileTo, id);
        ValidationUtil.assureIdConsistent(profileTo.getContacts(), id);
        ProfileUtil.checkContactsExist(profileTo.getContacts());
        Profile profile = profileMapper.updateFromTo(profileRepository.getOrCreate(profileTo.id()), profileTo);
        profileRepository.save(profile);
    }
}
