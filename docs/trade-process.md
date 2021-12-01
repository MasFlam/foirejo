# Trade Process
In order to buy a product, rent a service etc. a user can open a trade on an offer. This page
documents all the steps of the trade process. Payments are made through a fee-less escrow system.

1. The user opens a trade on an offer, and from now on will be called the buyer. The price they
   will have to pay in order to complete their side of the trade is going to stay constant in Monero
   from now on in this trade. The buyer is also given the payment wallet adress, to which they must
   send at least enough Monero to cover the price. If any extra Monero is sent to that address, it
   will be treated like a tip to the marketplace operators. It won't be sent to the seller or
   refunded, unless it's clearly a fat finger error.
2. Once the payment wallet receives enough Monero to cover the price, the seller ought complete the
   trade by mailing the product, sending a product key, or whatever is applicable to the offer
   in question, and only then marking the trade as completed and sending proof of completion.
3. Then the buyer can either confirm that the trade was actually completed, or open a dispute on the
   trade if they believe the seller has not completed their part of the trade.
   - If they confirm the completion of the trade, the seller is sent Monero from the payment wallet
     and the trade is closed.
   - If they open a dispute, an admin will intervene and make the final decision on whether to
     close the trade by refunding the buyer, or sending the appropriate amount of Monero to the
     sender's address.

What's important to note is that all communication concerning the trade between the buyer and the
seller should be conducted inside the trade chat, as in the event of a dispute no unverifiable
external communication records will be taken into account by the admin.
