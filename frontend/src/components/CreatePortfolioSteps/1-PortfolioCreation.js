import React, { Component } from "react";
import PortfolioDetails from "./2-PortfolioDetails";
import AddStocks from "./3-AddStocks";
import Confirm from "./4-Confirm";
import Success from "./5-Success";

export class PortfolioCreation extends Component {
    state = {
        step: 1,
        userId: null,
        portfolioName: "",
        portfolioDescription: "",
        stocks: [],
        stockQuantities: []
    };

    continues = (e) => {
        const { step } = this.state;
        this.setState({
            step: step + 1,
        });
    };

    back = (e) => {
        const { step } = this.state;
        this.setState({
            step: step - 1,
        });
    };

    handleChange = (input) => (e) => {
        this.setState({ [input]: e.target.value });
    };

    addStocks = (event, value) => {
        this.setState({ stocks: value });
        console.log("Updated stocks:", value.stocks);
    };

    handleEquityQuantities = (qty, equity) => {
        const newEquity = { equity: equity, quantity: qty };
        if (qty > 1) {
            this.setState(prevState => ({
                stockQuantities: [...prevState.stockQuantities, newEquity]
            }));
        }

    }

    render() {
        const { step } = this.state;
        const { userId, portfolioName, portfolioDescription, stocks, stockQuantities } = this.state;
        const values = { userId, portfolioName, portfolioDescription, stocks, stockQuantities };

        switch (step) {
            case 1:
                return (
                    <PortfolioDetails
                        handleChange={this.handleChange}
                        values={values}
                        continues={this.continues}
                    />
                );
            case 2:
                return (
                    <AddStocks
                        values={values}
                        back={this.back}
                        continues={this.continues}
                        addStocks={this.addStocks}
                        handleEquityQuantities={this.handleEquityQuantities}
                    />
                );
            case 3:
                return (
                    <Confirm
                        values={values}
                        back={this.back}
                        continues={this.continues}
                    />
                );
            case 4:
                return <Success />;

            default:
                return "Error";
        }
    }
}

export default PortfolioCreation;