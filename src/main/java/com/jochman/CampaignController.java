package com.jochman;

import com.jochman.entity.Campaign;
import com.jochman.entity.Keyword;
import com.jochman.entity.Product;
import com.jochman.request.CampaignCreationRequest;
import com.jochman.request.InitRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/campaign")
public class CampaignController {

    private final CampaignServiceImpl campaignService;

    @PostMapping("init-products-keywords-userAccountBalance")
    public ResponseEntity initProductsKeywordsUserAccountBalance(@RequestBody InitRequest initRequest){
        campaignService.initProductsKeywordsUserAccountBalance(initRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("create")
    public ResponseEntity postCampaign(@RequestBody CampaignCreationRequest campaignCreationRequest,
                                       @RequestParam("productId") Long productId){
        campaignService.saveCampaign(campaignCreationRequest, productId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("get-keywords")
    public ResponseEntity<List<Keyword>> getKeywords(){
        return new ResponseEntity<>(campaignService.getKeywords(), HttpStatus.OK);
    }

    @GetMapping("get-products")
    public ResponseEntity<List<Product>> getProducts(){
        return new ResponseEntity<>(campaignService.getProducts(), HttpStatus.OK);
    }

    @GetMapping("get-all")
    public ResponseEntity<List<Campaign>> getCampaign(){
        return new ResponseEntity<>(campaignService.getAllCampaigns(), HttpStatus.OK);
    }

    @GetMapping("get-user-account-balance")
    public ResponseEntity<Double> getUserAccountBalance(){
        return new ResponseEntity<>(campaignService.getUserEmeraldAccountBalance(), HttpStatus.OK);
    }

    @DeleteMapping("delete")
    public ResponseEntity deleteCampaign(@RequestParam("campaignId") Long campaignId){
        campaignService.deleteCampaign(campaignId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PutMapping("update")
    public ResponseEntity editCampaign(@RequestBody CampaignCreationRequest campaignCreationRequest,
                                       @RequestParam("campaignId") Long campaignId){
        campaignService.updateCampaign(campaignCreationRequest, campaignId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PatchMapping("change-status")
    public ResponseEntity changeStatus(@RequestParam Long campaignId){
        campaignService.changeStatus(campaignId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
