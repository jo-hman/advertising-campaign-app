package com.jochman;

import com.jochman.entity.Campaign;
import com.jochman.entity.Keyword;
import com.jochman.entity.Product;
import com.jochman.repository.CampaignRepository;
import com.jochman.repository.KeywordRepository;
import com.jochman.repository.ProductRepository;
import com.jochman.request.CampaignCreationRequest;
import com.jochman.request.InitRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CampaignServiceImpl implements CampaignService {

    private final CampaignRepository campaignRepository;
    private final KeywordRepository keywordRepository;
    private final ProductRepository productRepository;
    private Double userEmeraldAccountBalance;

    public void initProductsKeywordsUserAccountBalance(InitRequest initRequest) {
        productRepository.saveAll(initRequest.getProducts());
        keywordRepository.saveAll(initRequest.getKeywords());
        userEmeraldAccountBalance = initRequest.getUserAccountBalance();
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public void saveProducts(List<Product> products) {
        productRepository.saveAll(products);
    }

    @Override
    public void saveCampaign(CampaignCreationRequest campaignCreationRequest, Long productId) {
        Product product = productRepository.findById(productId).orElseThrow();

        if(userEmeraldAccountBalance - campaignCreationRequest.campaignFund() >= 0){
            userEmeraldAccountBalance -= campaignCreationRequest.campaignFund();
        }
        else throw new IllegalStateException("Not enough money");

        List<Keyword> keywords = generateKeywords(campaignCreationRequest.keywords());

        Campaign campaign = Campaign.builder().
                name(campaignCreationRequest.name()).
                keywords(keywords).
                bidAmount(campaignCreationRequest.bidAmount()).
                campaignFund(campaignCreationRequest.campaignFund()).
                isOn(false).
                town(campaignCreationRequest.town()).
                radius(campaignCreationRequest.radius()).
                product(product).
                build();
        product.getCampaigns().add(campaign);
        campaignRepository.save(campaign);
        productRepository.save(product);
    }

    @Override
    public List<Keyword> getKeywords() {
        return keywordRepository.findAll();
    }

    @Override
    public void updateCampaign(CampaignCreationRequest campaignCreationRequest, Long campaignId) {
        Campaign oldCampaign = campaignRepository.findById(campaignId).orElseThrow();

        if(userEmeraldAccountBalance + oldCampaign.getCampaignFund() - campaignCreationRequest.campaignFund() >= 0) {
            userEmeraldAccountBalance += oldCampaign.getCampaignFund() - campaignCreationRequest.campaignFund();
        }
        else throw new IllegalStateException("Not enough money");

        List<Keyword> keywords = generateKeywords(campaignCreationRequest.keywords());

        Campaign updatedCampaign = Campaign.builder().
                campaignId(campaignId).
                name(campaignCreationRequest.name()).
                keywords(keywords).
                bidAmount(campaignCreationRequest.bidAmount()).
                campaignFund(campaignCreationRequest.campaignFund()).
                isOn(oldCampaign.getIsOn()).
                town(campaignCreationRequest.town()).
                radius(campaignCreationRequest.radius()).
                build();

        campaignRepository.save(updatedCampaign);
    }

    @Override
    public void deleteCampaign(Long campaignId) {
        Campaign campaign = campaignRepository.findById(campaignId).orElseThrow();
        campaign.getProduct().getCampaigns().remove(campaign);
        productRepository.save(campaign.getProduct());
        campaignRepository.deleteById(campaignId);
    }

    @Override
    public List<Campaign> getAllCampaigns() {
        return campaignRepository.findAll();
    }

    @Override
    public void changeStatus(Long campaignId) {
        Campaign campaign = campaignRepository.findById(campaignId).orElseThrow();
        campaign.setIsOn(!campaign.getIsOn());
        campaignRepository.save(campaign);
    }

    @Override
    public Double getUserEmeraldAccountBalance() {
        return userEmeraldAccountBalance;
    }

    private List<Keyword> generateKeywords(List<Keyword> keywords){
        List<Keyword> generatedKeywords = new LinkedList<>();

        for(Keyword keyword : keywords){
            Optional<Keyword> opKeyword = keywordRepository.findByWord(keyword.getWord());
            if(opKeyword.isEmpty()){
                generatedKeywords.add(keyword);
            } else {
                keyword.setKeywordId(opKeyword.get().getKeywordId());
                generatedKeywords.add(keyword);
            }
        }

        return generatedKeywords;
    }
}
