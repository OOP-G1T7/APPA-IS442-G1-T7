package com.example.analyticsapp.portfolio;

import java.util.ArrayList;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PortfolioServiceImplementation implements PortfolioService {

    @Autowired
    private PortfolioRepository portfolioRepo;

    @Override
    public ArrayList<PortfolioEntity> retrieveAllPortfolios() {
        ArrayList<PortfolioEntity> result = portfolioRepo.getAllPortfolios();
        return result;
    }

    public ArrayList<PortfolioEntity> retrieveAllPortfoliosByUserId(int userId) {
        ArrayList<PortfolioEntity> result = portfolioRepo.getAllPortfoliosByUserId(userId);
        return result;
    }

    @Override
    public PortfolioEntity retrievePortfolio(int portfolioId) {
        PortfolioEntity retrieved = portfolioRepo.getPortfolio(portfolioId);
        return retrieved;
    }

    @Override
    public ResponseEntity<String> createPortfolio(PortfolioEntity newPortfolioEntity) {
        JSONObject response = new JSONObject();
        try {
            PortfolioEntity savedPortfolio = portfolioRepo.save(newPortfolioEntity); // Save and get the saved entity
            response.put("message", "Portfolio created successfully");
            response.put("portfolioId", savedPortfolio.getPortfolioId()); // Include the portfolioId in the response
            return new ResponseEntity<>(response.toString(), HttpStatus.OK);

        } catch (Exception e) {
            response.put("message", "Error occurred while creating portfolio");
            return new ResponseEntity<>(response.toString(), HttpStatus.BAD_GATEWAY);
        }
    }

    @Override
    public ResponseEntity<String> editPortfolio(PortfolioEntity updatedPortfolio) {
        JSONObject response = new JSONObject();
        int portfolioId = updatedPortfolio.getPortfolioId();
        if (portfolioRepo.findById(portfolioId).isPresent()) {

            try {
                portfolioRepo.save(updatedPortfolio);
                System.out.println("success");
                String message = "Portfolio with id " + portfolioId + " updated successfully";
                response.put("message", message);
                return new ResponseEntity<>(response.toString(), HttpStatus.OK);

            } catch (Exception e) {
                System.out.println("error");
                response.put("message", "Error occured while updating the portfolio");
                return new ResponseEntity<>(response.toString(), HttpStatus.BAD_GATEWAY);
            }

        } else {
            System.out.println("not found");
            response.put("message", "Portfolio not found");
            return new ResponseEntity<>(response.toString(), HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public ResponseEntity<String> deletePortfolio(int portfolioId) {
        JSONObject response = new JSONObject();
        if (portfolioRepo.findById(portfolioId).isPresent()) {
            portfolioRepo.deleteById(portfolioId);
            response.put("message", "Portfolio deleted successfully");
            return new ResponseEntity<>(response.toString(), HttpStatus.OK);
        } else {
            response.put("message", "Portfolio not found");
            return new ResponseEntity<>(response.toString(), HttpStatus.NOT_FOUND);
        }
    }
}
