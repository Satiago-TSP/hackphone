package hackphone.phone.detectors;

import gov.nist.javax.sip.header.To;
import hackphone.phone.configuration.SignallingContext;
import hackphone.phone.modifiers.ModifierContact;

import javax.sip.PeerUnavailableException;
import javax.sip.SipFactory;
import javax.sip.address.AddressFactory;
import javax.sip.message.MessageFactory;
import javax.sip.message.Request;
import javax.sip.message.Response;
import java.text.ParseException;

class ApiRequestInviteResponseBuilder {

    final MessageFactory messageFactory;
    final AddressFactory addressFactory;

    ApiRequestInviteResponseBuilder() {
        try {
            messageFactory = SipFactory.getInstance().createMessageFactory();
            addressFactory = SipFactory.getInstance().createAddressFactory();
        } catch (PeerUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    Response create(Request request, int statusCode, SignallingContext context) throws ParseException {
        Response response = messageFactory.createResponse(statusCode, request);
        if(statusCode == Response.RINGING) {
            To toHeader = (To) response.getHeader(To.NAME);
            toHeader.setTag("6af03043");    // TODO
            ModifierContact modifierContact = new ModifierContact();
            modifierContact.update(response, context);
        }
        if(statusCode== Response.BUSY_HERE) {
            To toHeader = (To) response.getHeader(To.NAME);
            toHeader.setTag("6af03043");    // TODO
        }
        return response;
    }
}
