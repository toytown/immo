package com.oas.web.main;

import org.apache.wicket.Component;
import org.apache.wicket.Request;
import org.apache.wicket.Response;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.Session;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebRequestCycleProcessor;
import org.apache.wicket.protocol.http.request.CryptedUrlWebRequestCodingStrategy;
import org.apache.wicket.protocol.http.request.WebRequestCodingStrategy;
import org.apache.wicket.request.IRequestCodingStrategy;
import org.apache.wicket.request.IRequestCycleProcessor;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

import com.oas.web.pages.CustomerActivationPage;
import com.oas.web.pages.EditPage;
import com.oas.web.pages.LoginPage;
import com.oas.web.pages.MainPage;
import com.oas.web.panels.EditTypePanel;

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 * 
 * @see oas.oas.Start#main(String[])
 */
public class OASApplication extends WebApplication {

	private static final String GOOGLE_MAPS_API_KEY_PARAM = "GoogleMapsAPIkey";

	/**
	 * Constructor
	 */
	public OASApplication() {
	}

	/**
	 * Covariant override for easy getting the current
	 * {@link GMapExampleApplication} without having to cast it.
	 */
	public static OASApplication get() {
		WebApplication webApplication = WebApplication.get();

		if (webApplication instanceof OASApplication == false) {
			throw new WicketRuntimeException("The application attached to the current thread is not a " + OASApplication.class.getSimpleName());
		}

		return (OASApplication) webApplication;
	}

	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	public Class<MainPage> getHomePage() {
		return MainPage.class;
	}

	/**
	 * @see org.apache.wicket.protocol.http.WebApplication#newSession(org.apache.wicket.Request,
	 *      Response)
	 */
	@Override
	public Session newSession(Request request, Response response) {
		return new OASSession(request);
	}

	/**
	 * @see org.apache.wicket.examples.WicketExampleApplication#init()
	 */
	@Override
	protected void init() {
		super.init();
		addComponentInstantiationListener(new SpringComponentInjector(this));

		getSecuritySettings().setAuthorizationStrategy(new IAuthorizationStrategy() {
			public boolean isActionAuthorized(Component component, Action action) {
				return true;

			}

			public <T extends Component> boolean isInstantiationAuthorized(Class<T> componentClass) {
				if (EditTypePanel.class.isAssignableFrom(componentClass)) {
					// Is user signed in?
					if (((OASSession) Session.get()).isSignedIn()) {
						// okay to proceed
						return true;
					}

					// Force sign in
					throw new RestartResponseAtInterceptPageException(LoginPage.class);
				}
				return true;
			}
		});

		mountBookmarkablePage("activate", CustomerActivationPage.class);
		mountBookmarkablePage("suchen", MainPage.class);
		mountBookmarkablePage("insert", EditPage.class);
	}

	/**
	 * @see org.apache.wicket.protocol.http.WebApplication#newRequestCycleProcessor()
	 */
	@Override
	protected IRequestCycleProcessor newRequestCycleProcessor() {
		return new WebRequestCycleProcessor() {
			@Override
			protected IRequestCodingStrategy newRequestCodingStrategy() {
				return new CryptedUrlWebRequestCodingStrategy(new WebRequestCodingStrategy());
			}
		};
	}

	/**
	 * Gets the init parameter 'GoogleMapsAPIkey' of the filter, or throws a
	 * WicketRuntimeException, if it is not set.
	 * 
	 * Pay attention at webapp deploy context, we need a different key for each
	 * deploy context check <a
	 * href="http://www.google.com/apis/maps/signup.html">Google Maps API - Sign
	 * Up</a> for more info.
	 * 
	 * Also the GClientGeocoder is pickier on this than the GMap2. Running on
	 * 'localhost' GMap2 will ignore the key and the maps will show up, but
	 * GClientGeocoder will not. So if the key doesn't match the url down to the
	 * directory GClientGeocoder will not work.
	 * 
	 * <pre>
	 * [...]
	 * &lt;init-param&gt;
	 *     &lt;param-name&gt;GoogleMapsAPIkey&lt;/param-name&gt;
	 *     &lt;param-value&gt;ABQIAAAAzaZpf6nHOd9w1PfLaM9u2xQRS2YPSd8S9D1NKPBvdB1fr18_CxR-svEYj6URCf5QDFq3i03mqrDlbA&lt;/param-value&gt;
	 * &lt;/init-param&gt;
	 * [...]
	 * </pre>
	 * 
	 * @return Google Maps API key
	 */
	public String getGoogleMapsAPIkey() {
		String googleMapsAPIkey = getInitParameter(GOOGLE_MAPS_API_KEY_PARAM);
		if (googleMapsAPIkey == null) {
			throw new WicketRuntimeException("There is no Google Maps API key configured in the " + "deployment descriptor of this application.");
		}
		return googleMapsAPIkey;
	}
}
