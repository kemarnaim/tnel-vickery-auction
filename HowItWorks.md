### Agents ###
  * Seller
  * Bidder(s)
  * Auctioneer ( VickreyNegotiationFacilitator )
  * **DF** (YellowPages)

### Behaviours: ###
  * SellerBehaviour
  * BidderBehaviour
  * AuctioneerBehaviour (AuctionInit)

### Messages ###

### Communication ###

| **NUM** | **SENDER** | **RECEIVER** | **MESSAGE**  | Description |
|:--------|:-----------|:-------------|:-------------|:------------|
| 1.      | Bidder1    | DF           | _register_   | register to DF |
| 2.      | Bidder2    | DF           | _register_   |register to DF |
| 3.      | Auctioneer | DF           | _register_   |register to DF |
| 4.      | Seller     | DF           | _register_   |register to DF |
| 5.      | Seller     | Auctioneer   | _REQUEST_    | check for bidders |
| 6.      | Auctioneer | DF           | _REQUEST_    | check for bidders |
| 7.      | Auctioneer | Bidder(s)    | _CFP_        | asks the bidders |
| 8.      | Bidder(s)  | Auctioneer   | _PPOROSE_    | make a bid  |
| 9.      | Bidder(s)  | Auctioneer   | _PPOROSE_    | make a bid  |
| 10.     | Auctioneer | Bidder(s)    | _REJECT\_PROPOSAL_ | bidder lose |
| 11.     | Auctioneer | Bidder(s)    | _ACCEPT\_PROPOSAL_ | bidder WINS |
| 12.     | Auctioneer | Seller       | _INFORM_     | bidder (n) WINS! |