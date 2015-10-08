package com.josedab.socialprofile.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.josedab.socialprofile.domain.Socialprofile;
import com.josedab.socialprofile.repository.SocialprofileRepository;
import com.josedab.socialprofile.web.rest.util.HeaderUtil;
import com.josedab.socialprofile.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Socialprofile.
 */
@RestController
@RequestMapping("/api")
public class SocialprofileResource {

    private final Logger log = LoggerFactory.getLogger(SocialprofileResource.class);

    @Inject
    private SocialprofileRepository socialprofileRepository;

    /**
     * POST  /socialprofiles -> Create a new socialprofile.
     */
    @RequestMapping(value = "/socialprofiles",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Socialprofile> create(@RequestBody Socialprofile socialprofile) throws URISyntaxException {
        log.debug("REST request to save Socialprofile : {}", socialprofile);
        if (socialprofile.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new socialprofile cannot already have an ID").body(null);
        }
        Socialprofile result = socialprofileRepository.save(socialprofile);
        return ResponseEntity.created(new URI("/api/socialprofiles/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("socialprofile", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /socialprofiles -> Updates an existing socialprofile.
     */
    @RequestMapping(value = "/socialprofiles",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Socialprofile> update(@RequestBody Socialprofile socialprofile) throws URISyntaxException {
        log.debug("REST request to update Socialprofile : {}", socialprofile);
        if (socialprofile.getId() == null) {
            return create(socialprofile);
        }
        Socialprofile result = socialprofileRepository.save(socialprofile);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("socialprofile", socialprofile.getId().toString()))
                .body(result);
    }

    /**
     * GET  /socialprofiles -> get all the socialprofiles.
     */
    @RequestMapping(value = "/socialprofiles",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Socialprofile>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Socialprofile> page = socialprofileRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/socialprofiles", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /socialprofiles/:id -> get the "id" socialprofile.
     */
    @RequestMapping(value = "/socialprofiles/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Socialprofile> get(@PathVariable Long id) {
        log.debug("REST request to get Socialprofile : {}", id);
        return Optional.ofNullable(socialprofileRepository.findOne(id))
            .map(socialprofile -> new ResponseEntity<>(
                socialprofile,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /socialprofiles/:id -> delete the "id" socialprofile.
     */
    @RequestMapping(value = "/socialprofiles/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete Socialprofile : {}", id);
        socialprofileRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("socialprofile", id.toString())).build();
    }
}
