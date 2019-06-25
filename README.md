# FlickrApp

Application that searches the Flickr Api for Photos who's title, description or tags contain the text will be returned. 

The application uses a scroll listener for the RecyclerView and it performs lazy loading once the user scrolls past a certain point.

The Database is the main source of truth for the UI and all the searches get inserted into it. If the device is offline and the user searches for a recent search term he will get the cached version.

MVVM Architecture that uses the Paging Library

Extra points:
  Allow for more than 25 results to be displayed
  Error Handling
  View Searches offlines of RECENT searches

[![bleacherreport.png](https://i.postimg.cc/pXBtqvg0/bleacherreport.png)](https://postimg.cc/4mnMxD4t)
