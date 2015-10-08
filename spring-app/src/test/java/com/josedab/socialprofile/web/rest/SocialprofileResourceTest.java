package com.josedab.socialprofile.web.rest;

import com.josedab.socialprofile.Application;
import com.josedab.socialprofile.domain.Socialprofile;
import com.josedab.socialprofile.repository.SocialprofileRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the SocialprofileResource REST controller.
 *
 * @see SocialprofileResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SocialprofileResourceTest {

    private static final String DEFAULT_TWITTER = "SAMPLE_TEXT";
    private static final String UPDATED_TWITTER = "UPDATED_TEXT";
    private static final String DEFAULT_FACEBOOK = "SAMPLE_TEXT";
    private static final String UPDATED_FACEBOOK = "UPDATED_TEXT";
    private static final String DEFAULT_EMAIL = "SAMPLE_TEXT";
    private static final String UPDATED_EMAIL = "UPDATED_TEXT";
    private static final String DEFAULT_INSTAGRAM = "SAMPLE_TEXT";
    private static final String UPDATED_INSTAGRAM = "UPDATED_TEXT";
    private static final String DEFAULT_PINTEREST = "SAMPLE_TEXT";
    private static final String UPDATED_PINTEREST = "UPDATED_TEXT";
    private static final String DEFAULT_GOOGLEPLUS = "SAMPLE_TEXT";
    private static final String UPDATED_GOOGLEPLUS = "UPDATED_TEXT";

    @Inject
    private SocialprofileRepository socialprofileRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restSocialprofileMockMvc;

    private Socialprofile socialprofile;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SocialprofileResource socialprofileResource = new SocialprofileResource();
        ReflectionTestUtils.setField(socialprofileResource, "socialprofileRepository", socialprofileRepository);
        this.restSocialprofileMockMvc = MockMvcBuilders.standaloneSetup(socialprofileResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        socialprofile = new Socialprofile();
        socialprofile.setTwitter(DEFAULT_TWITTER);
        socialprofile.setFacebook(DEFAULT_FACEBOOK);
        socialprofile.setEmail(DEFAULT_EMAIL);
        socialprofile.setInstagram(DEFAULT_INSTAGRAM);
        socialprofile.setPinterest(DEFAULT_PINTEREST);
        socialprofile.setGoogleplus(DEFAULT_GOOGLEPLUS);
    }

    @Test
    @Transactional
    public void createSocialprofile() throws Exception {
        int databaseSizeBeforeCreate = socialprofileRepository.findAll().size();

        // Create the Socialprofile

        restSocialprofileMockMvc.perform(post("/api/socialprofiles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(socialprofile)))
                .andExpect(status().isCreated());

        // Validate the Socialprofile in the database
        List<Socialprofile> socialprofiles = socialprofileRepository.findAll();
        assertThat(socialprofiles).hasSize(databaseSizeBeforeCreate + 1);
        Socialprofile testSocialprofile = socialprofiles.get(socialprofiles.size() - 1);
        assertThat(testSocialprofile.getTwitter()).isEqualTo(DEFAULT_TWITTER);
        assertThat(testSocialprofile.getFacebook()).isEqualTo(DEFAULT_FACEBOOK);
        assertThat(testSocialprofile.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testSocialprofile.getInstagram()).isEqualTo(DEFAULT_INSTAGRAM);
        assertThat(testSocialprofile.getPinterest()).isEqualTo(DEFAULT_PINTEREST);
        assertThat(testSocialprofile.getGoogleplus()).isEqualTo(DEFAULT_GOOGLEPLUS);
    }

    @Test
    @Transactional
    public void getAllSocialprofiles() throws Exception {
        // Initialize the database
        socialprofileRepository.saveAndFlush(socialprofile);

        // Get all the socialprofiles
        restSocialprofileMockMvc.perform(get("/api/socialprofiles"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(socialprofile.getId().intValue())))
                .andExpect(jsonPath("$.[*].twitter").value(hasItem(DEFAULT_TWITTER.toString())))
                .andExpect(jsonPath("$.[*].facebook").value(hasItem(DEFAULT_FACEBOOK.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].instagram").value(hasItem(DEFAULT_INSTAGRAM.toString())))
                .andExpect(jsonPath("$.[*].pinterest").value(hasItem(DEFAULT_PINTEREST.toString())))
                .andExpect(jsonPath("$.[*].googleplus").value(hasItem(DEFAULT_GOOGLEPLUS.toString())));
    }

    @Test
    @Transactional
    public void getSocialprofile() throws Exception {
        // Initialize the database
        socialprofileRepository.saveAndFlush(socialprofile);

        // Get the socialprofile
        restSocialprofileMockMvc.perform(get("/api/socialprofiles/{id}", socialprofile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(socialprofile.getId().intValue()))
            .andExpect(jsonPath("$.twitter").value(DEFAULT_TWITTER.toString()))
            .andExpect(jsonPath("$.facebook").value(DEFAULT_FACEBOOK.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.instagram").value(DEFAULT_INSTAGRAM.toString()))
            .andExpect(jsonPath("$.pinterest").value(DEFAULT_PINTEREST.toString()))
            .andExpect(jsonPath("$.googleplus").value(DEFAULT_GOOGLEPLUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSocialprofile() throws Exception {
        // Get the socialprofile
        restSocialprofileMockMvc.perform(get("/api/socialprofiles/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSocialprofile() throws Exception {
        // Initialize the database
        socialprofileRepository.saveAndFlush(socialprofile);

		int databaseSizeBeforeUpdate = socialprofileRepository.findAll().size();

        // Update the socialprofile
        socialprofile.setTwitter(UPDATED_TWITTER);
        socialprofile.setFacebook(UPDATED_FACEBOOK);
        socialprofile.setEmail(UPDATED_EMAIL);
        socialprofile.setInstagram(UPDATED_INSTAGRAM);
        socialprofile.setPinterest(UPDATED_PINTEREST);
        socialprofile.setGoogleplus(UPDATED_GOOGLEPLUS);
        

        restSocialprofileMockMvc.perform(put("/api/socialprofiles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(socialprofile)))
                .andExpect(status().isOk());

        // Validate the Socialprofile in the database
        List<Socialprofile> socialprofiles = socialprofileRepository.findAll();
        assertThat(socialprofiles).hasSize(databaseSizeBeforeUpdate);
        Socialprofile testSocialprofile = socialprofiles.get(socialprofiles.size() - 1);
        assertThat(testSocialprofile.getTwitter()).isEqualTo(UPDATED_TWITTER);
        assertThat(testSocialprofile.getFacebook()).isEqualTo(UPDATED_FACEBOOK);
        assertThat(testSocialprofile.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testSocialprofile.getInstagram()).isEqualTo(UPDATED_INSTAGRAM);
        assertThat(testSocialprofile.getPinterest()).isEqualTo(UPDATED_PINTEREST);
        assertThat(testSocialprofile.getGoogleplus()).isEqualTo(UPDATED_GOOGLEPLUS);
    }

    @Test
    @Transactional
    public void deleteSocialprofile() throws Exception {
        // Initialize the database
        socialprofileRepository.saveAndFlush(socialprofile);

		int databaseSizeBeforeDelete = socialprofileRepository.findAll().size();

        // Get the socialprofile
        restSocialprofileMockMvc.perform(delete("/api/socialprofiles/{id}", socialprofile.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Socialprofile> socialprofiles = socialprofileRepository.findAll();
        assertThat(socialprofiles).hasSize(databaseSizeBeforeDelete - 1);
    }
}
