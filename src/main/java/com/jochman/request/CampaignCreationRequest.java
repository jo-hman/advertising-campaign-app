package com.jochman.request;

import com.jochman.entity.Keyword;

import java.util.List;

public record CampaignCreationRequest(String name,
                                      List<Keyword> keywords,
                                      Double bidAmount,
                                      Double campaignFund,
                                      String town,
                                      Double radius) {
}
