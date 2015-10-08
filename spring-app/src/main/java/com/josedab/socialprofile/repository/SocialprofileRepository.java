package com.josedab.socialprofile.repository;

import com.josedab.socialprofile.domain.Socialprofile;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Socialprofile entity.
 */
public interface SocialprofileRepository extends JpaRepository<Socialprofile,Long> {

}
