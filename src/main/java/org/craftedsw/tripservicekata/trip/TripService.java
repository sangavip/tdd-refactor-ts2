package org.craftedsw.tripservicekata.trip;

import java.util.ArrayList;
import java.util.List;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;
import org.springframework.beans.factory.annotation.Autowired;

public class TripService {

	@Autowired
	private TripDAO tripDAO;
	
	/**
	 * 
	 * @param user
	 * @return
	 * @throws UserNotLoggedInException
	 * @should _throw_an_exception_when_user_not_logged_in
	 * @should _return_empty_trip_when_user_has_no_friends
	 * @should _return_empty_trip_when_user_has_friends_and_no_trips
	 * @should _return_empty_trip_when_user_has_friends_and_has_many_trips
	 * @should _return_empty_trip_when_user_has_friends_excluding_logged_user_and_no_trips
	 */
	public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {

		return user.isAFriendOf(validatedLoggedInUser()) 
				? findTripsByUser(user) 
				: emptyTrips();

	}

	protected ArrayList<Trip> emptyTrips() {
		return new ArrayList<Trip>();
	}

	protected User validatedLoggedInUser() {
		if (getLoggedInUser() != null) {
			return getLoggedInUser();
		}
		throw new UserNotLoggedInException();
	}

	//Use DI
	protected List<Trip> findTripsByUser(User user) {
		return tripDAO.findTripsByUser(user);
	}

	// Its a functionality of web layer.
	protected User getLoggedInUser() {
		return UserSession.getInstance().getLoggedInUser();
	}

}
