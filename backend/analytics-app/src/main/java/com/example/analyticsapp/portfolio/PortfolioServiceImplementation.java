package com.example.analyticsapp.portfolio;

import java.util.ArrayList;
// import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.analyticsapp.stock.StockEntity;
// import com.example.analyticsapp.stock.StockEntityDTO;
import com.example.analyticsapp.stock.StockEntityDTO;

@Qualifier("PortfolioServiceImpl")
@Service
public class PortfolioServiceImplementation implements PortfolioService{

    @Autowired
    PortfolioRepository portfolioRepository;

    public ArrayList<PortfolioEntity> getAll() {
        ArrayList<PortfolioEntity> result = portfolioRepository.getAll();
        return result;
    }


    public ArrayList<PortfolioEntity> getAllPortfolios(int userId) {
        ArrayList<PortfolioEntity> result = portfolioRepository.getAllPortfolios(userId);
        return result;
    }

    public PortfolioEntity getOnePortfolio(int portfolioId) {
        PortfolioEntity result = portfolioRepository.getOnePortfolio(portfolioId);
        return result;
    }

    // public PortfolioWithStocksDTO getPortfolioWithStocks(int portfolioId) {
    //     PortfolioEntity portfolio = portfolioRepository.getPortfolioWithStocks(portfolioId);
    //     return createPortfolioDTO(portfolio);
    // }

    // public PortfolioWithStocksDTO createPortfolioDTO(PortfolioEntity portfolio) {
    //     PortfolioWithStocksDTO dto = new PortfolioWithStocksDTO();
    //     dto.setPortfolioId(portfolio.getPortfolioId());
    //     dto.setName(portfolio.getName());
    //     dto.setUserId(portfolio.getUserId());
    //     dto.setDescription(portfolio.getDescription());

    //     // Map associated StockEntity objects to StockDTOs
    //     List<StockEntityDTO> stockDTOs = new ArrayList<>();
    //     for (StockEntity stockEntity : portfolio.getStocks()) {
    //         StockEntityDTO stockDTO = new StockEntityDTO();
    //         stockDTO.setTicker(stockEntity.getTicker());
    //         stockDTO.setPortfolioId(stockEntity.getPortfolioId());
    //         stockDTO.setQuantity(stockEntity.getQuantity());
    //         stockDTOs.add(stockDTO);
    //     }

    //     dto.setStocks(stockDTOs);

    //     return dto;
    // }

    public PortfolioEntity addStockToPortfolio(StockEntityDTO stockDTO) {
        PortfolioEntity portfolio = portfolioRepository.getOnePortfolio(stockDTO.getPortfolioId());
        StockEntity stock = new StockEntity();
        stock.setTicker(stockDTO.getTicker());
        stock.setPortfolioId(stockDTO.getPortfolioId());
        stock.setQuantity(stockDTO.getQuantity());
        portfolio.getStocks().add(stock);
        portfolioRepository.save(portfolio);
        return portfolio;
    }

}