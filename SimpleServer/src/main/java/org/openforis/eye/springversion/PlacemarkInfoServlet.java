package org.openforis.eye.springversion;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PlacemarkInfoServlet extends JsonPocessorServlet {


	private String getPlacemarkId(Map<String, String> collectedData) {
		return collectedData.get(ServerInitilizer.PLACEMARK_ID);
	}
	@Override
	@RequestMapping("/placemarkInfo")
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, String> collectedData = extractRequestData(request);

		String placemarkId = getPlacemarkId(collectedData);

		// REMOVE THIS!!!!¬
		placemarkId = "testPlacemark";

		if (placemarkId == null) {
			setResult(false, "No placemark ID found in the request", collectedData);
			getLogger().error("No placemark ID found in the received request");
		} else {

			collectedData = getDataAccessor().getData(placemarkId);
			if (collectedData != null && collectedData.size() > 0) {

				setResult(true, "The placemark was found", collectedData);
				getLogger().info("A placemark was found with these properties" + collectedData.toString());

			} else {
				if (collectedData == null) {
					collectedData = new HashMap<String, String>();
				}
				setResult(false, "No placemark found", collectedData);
				getLogger().info("No placemark found " + collectedData.toString());
			}
		}

		setJsonResponse(response, collectedData);

	}

}
