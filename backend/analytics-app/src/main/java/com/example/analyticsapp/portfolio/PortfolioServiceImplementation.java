package com.example.analyticsapp.portfolio;

import java.util.ArrayList;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    public PortfolioEntity createPortfolio(PortfolioEntity newPortfolioEntity) {
        return portfolioRepo.save(newPortfolioEntity);
    }

    @Override
    public PortfolioEntity editPortfolio(PortfolioEntity updatedPortfolio) {
        return portfolioRepo.save(updatedPortfolio);
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
