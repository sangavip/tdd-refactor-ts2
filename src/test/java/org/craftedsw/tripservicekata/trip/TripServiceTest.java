package org.craftedsw.tripservicekata.trip;

import java.util.ArrayList;
import java.util.List;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import junit.framework.Assert;

@RunWith(MockitoJUnitRunner.class)
public class TripServiceTest {

	@Spy
	private TripService tripService = new TripService();

	/**
	 * @see TripService#getTripsByUser(User)
	 * @verifies _throw_an_exception_when_user_not_logged_in
	 */
	@Test(expected = UserNotLoggedInException.class)
	public void getTripsByUser_should_throw_an_exception_when_user_not_logged_in() throws Exception {
		Mockito.doReturn(null).when(tripService).getLoggedInUser();
		tripService.getTripsByUser(null);
	}

	/**
	 * @see TripService#getTripsByUser(User)
	 * @verifies _return_empty_trip_when_user_has_no_friends
	 */
	@Test
	public void getTripsByUser_should_return_empty_trip_when_user_has_no_friends() throws Exception {
		Mockito.doReturn(new User()).when(tripService).getLoggedInUser();
		List<Trip> trip = tripService.getTripsByUser(new User());
		Assert.assertEquals(0, trip.size());
	}

	/**
	 * @see TripService#getTripsByUser(User)
	 * @verifies _return_empty_trip_when_user_has_friends_and_no_trips
	 */
	@Test
	public void getTripsByUser_should_return_empty_trip_when_user_has_friends_and_no_trips() throws Exception {
		
		User loggedUser = new User();
		User friendUser = new User();
		friendUser.addFriend(loggedUser);
		
		Mockito.doReturn(loggedUser).when(tripService).getLoggedInUser();
		Mockito.doReturn(new ArrayList<Trip>()).when(tripService).findTripsByUser(friendUser);
		
		List<Trip> trip = tripService.getTripsByUser(friendUser);
		Assert.assertEquals(0, trip.size());
	}

	/**
	 * @see TripService#getTripsByUser(User)
	 * @verifies _return_empty_trip_when_user_has_friends_excluding_logged_user_and_no_trips
	 */
	@Test
	public void getTripsByUser_should_return_empty_trip_when_user_has_friends_excluding_logged_user_and_no_trips()
			throws Exception {
		
		User loggedUser = new User();
		User friendUser = new User();
		friendUser.addFriend(new User());
		friendUser.addFriend(new User());
		
		Mockito.doReturn(loggedUser).when(tripService).getLoggedInUser();
		Mockito.doReturn(new ArrayList<Trip>()).when(tripService).findTripsByUser(friendUser);
		
		List<Trip> trip = tripService.getTripsByUser(friendUser);
		Assert.assertEquals(0, trip.size());
	}

	/**
	 * @see TripService#getTripsByUser(User)
	 * @verifies _return_empty_trip_when_user_has_friends_and_has_many_trips
	 */
	@Test
	public void getTripsByUser_should_return_empty_trip_when_user_has_friends_and_has_many_trips() throws Exception {
		
		User loggedUser = new User();
		User friendUser = new User();
		friendUser.addFriend(loggedUser);
		friendUser.addFriend(new User());
		friendUser.addTrip(new Trip());
		friendUser.addTrip(new Trip());
		
		Mockito.doReturn(loggedUser).when(tripService).getLoggedInUser();
		Mockito.doReturn(friendUser.trips()).when(tripService).findTripsByUser(friendUser);
		
		List<Trip> trip = tripService.getTripsByUser(friendUser);
		Assert.assertEquals(2, trip.size());
	}
}