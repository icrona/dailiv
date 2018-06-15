package com.dailiv.view.profile.edit;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.dailiv.App;
import com.dailiv.R;
import com.dailiv.internal.data.local.pojo.EditProfile;
import com.dailiv.internal.injector.component.DaggerActivityComponent;
import com.dailiv.internal.injector.module.ActivityModule;
import com.dailiv.util.IConstants;
import com.dailiv.view.base.AbstractActivity;

import org.parceler.Parcels;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.dailiv.util.IConstants.PICK_PHOTO_REQUEST;
import static com.dailiv.util.IConstants.READ_EXTERNAL_PERMISSION;
import static com.dailiv.util.common.FileUtil.getFile;
import static com.dailiv.util.common.GlideUtil.glide;

/**
 * Created by aldo on 6/2/18.
 */

public class EditProfileActivity extends AbstractActivity implements EditProfileView{

    @Inject
    EditProfilePresenter presenter;

    @BindView(R.id.toolbar_edit_profile)
    Toolbar toolbar;

    @BindView(R.id.civ_change_photo)
    CircleImageView civChangePhoto;

    @BindView(R.id.civ_user_photo)
    CircleImageView civUserPhoto;

    @BindView(R.id.et_headline)
    EditText etHeadline;

    @BindView(R.id.tv_user_name)
    TextView tvUsername;

    @BindView(R.id.tv_user_email)
    TextView tvEmail;

    @BindView(R.id.tv_user_phone)
    TextView tvPhone;

    private EditProfile editProfile;

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
    public void onShowProgressBar() {

    }

    @Override
    public void onHideProgressBar() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_edit_profile;
    }

    @Override
    protected void initComponents(Bundle savedInstanceState) {
        inject();
        onAttach();
        setToolbar();
        setEditProfile();
        setChangePhoto();
        setEditHeadline();
    }

    private void setToolbar() {
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        toolbar.setNavigationIcon(
                ContextCompat.getDrawable(this, R.drawable.ic_back));
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


    @Override
    public void onChangeProfilePhoto() {

    }

    @Override
    public void onEditHeadline() {

    }

    private void setEditProfile() {

        editProfile = Parcels.unwrap(getIntent().getParcelableExtra(IConstants.EDIT_PROFILE));

        glide(civUserPhoto, editProfile.getPhotoUrl());

        tvUsername.setText(editProfile.getFullName());

        etHeadline.setText(editProfile.getHeadline());

        tvEmail.setText(editProfile.getEmail());

        tvPhone.setText(editProfile.getPhoneNumber());

    }

    private void setEditHeadline() {

        etHeadline.setImeOptions(EditorInfo.IME_ACTION_DONE);
        etHeadline.setRawInputType(InputType.TYPE_CLASS_TEXT);

        etHeadline.setOnEditorActionListener((textView, i, keyEvent) -> {

            if (i == EditorInfo.IME_ACTION_DONE) {

                editHeadline(etHeadline.getText().toString());

                View view = this.getCurrentFocus();

                etHeadline.clearFocus();

                if (view != null) {

                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

                    if(imm != null) {
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                }

                return true;

            }
            return false;
        });

    }

    private void editHeadline(String headline) {
        presenter.editHeadline(headline);
    }

    private void setChangePhoto() {

        civChangePhoto.setOnClickListener(view -> checkPermissionAndPickPhotoFromGallery());

    }

    public void checkPermissionAndPickPhotoFromGallery() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_PERMISSION);
        }
        else {
            pickPhotoFromGallery();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {

        if(requestCode == READ_EXTERNAL_PERMISSION && ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED))) {
            pickPhotoFromGallery();
        }

    }


    private void pickPhotoFromGallery(){

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Profile Photo"), PICK_PHOTO_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PICK_PHOTO_REQUEST && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            File file = getFile(this, uri);
            onUploadFile(file);

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                civUserPhoto.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onUploadFile(File file) {
        if(file == null) {

            return;
        }

        presenter.changeProfilePhoto(file);
    }
}
