package com.jochman;

import com.jochman.entity.Campaign;
import com.jochman.entity.Keyword;
import com.jochman.entity.Product;
import com.jochman.request.CampaignCreationRequest;

import java.util.List;

public interface CampaignService {
     void saveCampaign(CampaignCreationRequest campaignCreationRequest, Long productId);
     void updateCampaign(CampaignCreationRequest campaignCreationRequest, Long campaignId);
     void deleteCampaign(Long campaignId);
     List<Campaign> getAllCampaigns();
     void saveProducts(List<Product> products);
     List<Keyword> getKeywords();
     void changeStatus(Long campaignId);
     Double getUserEmeraldAccountBalance();
     List<Product> getProducts();
}
