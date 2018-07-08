package com.dailiv.view.location;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dailiv.App;
import com.dailiv.R;
import com.dailiv.internal.data.remote.request.location.AddLocationRequest;
import com.dailiv.internal.data.remote.response.location.LocationResponse;
import com.dailiv.internal.injector.component.DaggerActivityComponent;
import com.dailiv.internal.injector.module.ActivityModule;
import com.dailiv.view.base.AbstractActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.dailiv.util.common.Preferences.setLocation;


public class LocationActivity extends AbstractActivity implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,LocationView {

    private GoogleMap mMap;

    private static final int PERMISSION_REQUEST_CODE = 7001;
    private static final int PLAY_SERVICE_REQUEST = 7002;

    private static final int UPDATE_INTERVAL = 5000;
    private static final int FASTEST_INTERVAL = 3000;
    private static final int DISPLACEMENT = 10;

    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;

    Marker marker;

    private AddLocationRequest addLocationRequest;

    private LocationResponse suggestedLocation;

    @BindView(R.id.toolbar_location)
    Toolbar toolbar;

    @BindView(R.id.rb_location)
    RadioButton rbLocation;

    @BindView(R.id.tv_or_use_this_location)
    TextView tvOrUseThisLocation;

    @Inject
    LocationPresenter presenter;

    @Override
    public void onDetach() {
        presenter.onDetach();
    }

    @Override
    public void inject() {
        DaggerActivityComponent.builder()
                .applicationComponent(App.getComponent())
                .activityModule(new ActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onAttach() {
        presenter.onAttach(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_location;
    }

    @Override
    protected void initComponents(Bundle savedInstanceState) {
        inject();
        onAttach();
        setToolbar();

        presenter.getLocation();

        setupPlaceAutoComplete();
    }

    private void setToolbar() {
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        toolbar.setNavigationIcon(
                ContextCompat.getDrawable(this, R.drawable.ic_back));
        toolbar.setTitle("Choose Location");
        toolbar.hideOverflowMenu();
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupPlaceAutoComplete() {
        PlaceAutocompleteFragment placeAutocompleteFragment = (PlaceAutocompleteFragment)getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        placeAutocompleteFragment.setFilter(new AutocompleteFilter.Builder().setCountry("ID").build());

        placeAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                final LatLng latLngLoc = place.getLatLng();

                if(marker!=null){
                    marker.remove();
                }
                marker = mMap.addMarker(new MarkerOptions().position(latLngLoc).title(place.getName().toString()));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latLngLoc.latitude, latLngLoc.longitude), 15.0f));

                selectCurrentPlace(place);
            }

            @Override
            public void onError(Status status) {
                Toast.makeText(LocationActivity.this, ""+status.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        setUpLocation();
    }


    public AddLocationRequest getAddLocationRequest() {
        return addLocationRequest;
    }

    public void setAddLocationRequest(AddLocationRequest addLocationRequest) {
        this.addLocationRequest = addLocationRequest;
    }

    private void selectCurrentPlace(Place place) {
        AddLocationRequest addLocationRequest = new AddLocationRequest();
        addLocationRequest.formattedAddress = place.getAddress().toString();
        addLocationRequest.placeId = place.getId();
        addLocationRequest.latitude = place.getLatLng().latitude;
        addLocationRequest.longitude = place.getLatLng().longitude;
        setAddLocationRequest(addLocationRequest);

    }

    private void setUpLocation() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, PERMISSION_REQUEST_CODE);
        }
        else
        {
            if(checkPlayServices())
            {
                buildGoogleApiClient();
                createLocationRequest();
                displayLocation();
            }
        }
    }


    private boolean checkPlayServices() {

        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int resultCode = googleAPI.isGooglePlayServicesAvailable(this);

        if(resultCode != ConnectionResult.SUCCESS)
        {
            if(googleAPI.isUserResolvableError(resultCode))
                googleAPI.getErrorDialog(this, resultCode, PLAY_SERVICE_REQUEST).show();
            else
            {
                Toast.makeText(this, "This device is not supported", Toast.LENGTH_SHORT).show();
                finish();
            }
            return false;
        }
        return true;
    }


    private void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    private void createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
    }

    private void displayLocation() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
        {
            return;
        }

        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(mLocation!=null)
        {
            final double latitude = mLocation.getLatitude();
            final double longitude = mLocation.getLongitude();

            mMap.setMyLocationEnabled(true);

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15.0f));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case PERMISSION_REQUEST_CODE:
                if(grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    if(checkPlayServices())
                    {
                        buildGoogleApiClient();
                        createLocationRequest();
                        displayLocation();
                    }
                }
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        displayLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLocation = location;
        displayLocation();
    }

    @OnClick(R.id.btn_choose_location)
    public void chooseLocation() {
        //todo check checkbox

        if(rbLocation.isChecked() && getSuggestedLocation() != null) {
            presenter.chooseLocation(getSuggestedLocation().id);
        }

        else if(getAddLocationRequest() == null) {
            Toast.makeText(this, R.string.please_choose_location, Toast.LENGTH_SHORT).show();
        }
        else{
            presenter.addLocation(getAddLocationRequest());
        }
    }


    @Override
    public void onShowProgressBar() {

    }

    @Override
    public void onHideProgressBar() {

    }

    @Override
    public void onLocationChosen(LocationResponse locationResponse) {
        setLocation(locationResponse);
        finish();
    }

    public LocationResponse getSuggestedLocation() {
        return suggestedLocation;
    }

    public void setSuggestedLocation(LocationResponse suggestedLocation) {
        this.suggestedLocation = suggestedLocation;
    }

    @Override
    public void onGetLocation(LocationResponse locationResponse) {

        if(locationResponse == null) {
            return;
        }

        setSuggestedLocation(locationResponse);
        rbLocation.setVisibility(View.VISIBLE);
        rbLocation.setText(locationResponse.address);

        rbLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    tvOrUseThisLocation.setVisibility(View.VISIBLE);
                }

                else {
                    tvOrUseThisLocation.setVisibility(View.INVISIBLE);
                }
            }
        });

    }
}
