

private const val EXTRA_PROFILE_TIER = "EXTRA_PROFILE_TIER"

/*class UpdatePassportFragment : BaseFragment(R.layout.fragment_update_passport),
    UpdatePassportContract.View, MediaPickerDialog.OnMediaItemSelectedListener {

    companion object {
        fun create(profileTier:
         ProfileTier) = UpdatePassportFragment().withArgs(
            EXTRA_PROFILE_TIER to profileTier
        )
    }

    private val presenter: UpdatePassportPresenter by presenter()
    private val calendar: Calendar by lazy { Calendar.getInstance() }
    private val profileTier: ProfileTier by args(EXTRA_PROFILE_TIER)
    private val formatter: SimpleDateFormat by lazy {
        SimpleDateFormat(
            "dd MMMM yyyy",
            Locale.getDefault()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            popScreen()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actionBar {
            setTitle(R.string.profile_passport_data)
            setNavigationOnClickListener { popScreen() }
        }
        setupViews(profileTier == ProfileTier.Elementary)
        presenter.loadPassport()
    }

    private fun setupViews(isEnabled: Boolean) {
        passportIdEditText.isEnabled = isEnabled
        issueAuthorityEditText.isEnabled = isEnabled
        iinEditText.isEnabled = isEnabled
        issueDateEditText.isEnabled = isEnabled
        endIssueDateEditText.isEnabled = isEnabled
        birthEditText.isEnabled = isEnabled
        frontPassportLayout.isEnabled = isEnabled
        backPassportLayout.isEnabled = isEnabled
        progressButton.isVisible = isEnabled
        if (isEnabled) {
            passportIdEditText.afterTextChanged {
                presenter.onPassportIdChanged(it)
            }
            issueAuthorityEditText.afterTextChanged {
                presenter.onIssueAuthorityChanged(it)
            }
            iinEditText.afterTextChanged {
                presenter.onIinChanged(it)
            }
            progressButton.setThrottleOnClickListener {
                presenter.update()
            }
            issueDateEditText.setThrottleOnClickListener {
                presenter.onIssueDate()
            }
            endIssueDateEditText.setThrottleOnClickListener {
                presenter.onEndIssueDate()
            }
            birthEditText.setThrottleOnClickListener {
                presenter.onBirthDate()
            }
            frontPassportLayout.setThrottleOnClickListener {
                presenter.onFrontPassportImage()
            }
            backPassportLayout.setThrottleOnClickListener {
                presenter.onBackPassportImage()
            }
        }
    }

    override fun showLoading(isLoading: Boolean) {
        progressButton.setLoading(isLoading)
    }

    override fun showFrontPassportInfo(isEnabled: Boolean) {
        val drawable = if (isEnabled) R.drawable.ic_tick else 0
        frontPassportLayout.setCompoundDrawablesWithIntrinsicBounds(
            R.drawable.ic_passport_info,
            0,
            drawable,
            0
        )
        frontPassportLayout.isActivated = isEnabled
    }

    override fun showBackPassportInfo(isEnabled: Boolean) {
        val drawable = if (isEnabled) R.drawable.ic_tick else 0
        backPassportLayout.setCompoundDrawablesWithIntrinsicBounds(
            R.drawable.ic_passport_info,
            0,
            drawable,
            0
        )
        backPassportLayout.isActivated = isEnabled
    }

    override fun showBirthDate(birthdayMillis: Long?) {
        requireContext().showDatePicker(
            date = calendar.apply { birthdayMillis?.let { timeInMillis = it } },
            listener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                presenter.onBirthDateChanged(calendar.timeInMillis)
            }).show()
    }

    override fun updateBirthday(date: Long?) {
        birthEditText.setText(date?.let { formatter.format(it) })
    }

    override fun updateIssueDate(date: Long?) {
        issueDateEditText.setText(date?.let { formatter.format(it) })
    }

    override fun updateExpireDate(date: Long?) {
        endIssueDateEditText.setText(date?.let { formatter.format(it) })
    }

    override fun onPassportUploaded(frontImage: String?, backImage: String?) {
        showFrontPassportInfo(!frontImage.isNullOrEmpty())
        showBackPassportInfo(!backImage.isNullOrEmpty())
    }

    override fun showEndIssueDate(endIssueMillis: Long?) {
        requireContext().showDatePicker(
            date = calendar.apply { endIssueMillis?.let { timeInMillis = it } },
            listener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                presenter.onExpireDateChanged(calendar.timeInMillis)
            }).show()
    }

    override fun showIssueDate(issueMillis: Long?) {
        requireContext().showDatePicker(
            date = calendar.apply { issueMillis?.let { timeInMillis = it } },
            listener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                presenter.onIssueDateChanged(calendar.timeInMillis)
            }).show()
    }

    override fun showPassportImage() {
        MediaPickerDialog.show(
            childFragmentManager,
            PickerOptions(
                maxFiles = 1,
                showIconsOptions = ShowIconsOptions(
                    showCameraIcon = true,
                    showPickFromGalleryIcon = false
                ),
                shouldPreviewPhoto = true
            )
        )
    }

    override fun showPassport(
        birthDate: Long?,
        passportNumber: String,
        issueDate: Long?,
        expireDate: Long?,
        passportAuthority: String,
        tin: String,
        frontImageUploaded: Boolean,
        backImageUploaded: Boolean
    ) {
        birthEditText.setText(birthDate?.let {
            calendar.timeInMillis = it
            formatter.format(calendar.time)
        })
        issueDateEditText.setText(issueDate?.let {
            calendar.timeInMillis = it
            formatter.format(calendar.time)
        })
        endIssueDateEditText.setText(expireDate?.let {
            calendar.timeInMillis = it
            formatter.format(calendar.time)
        })
        issueAuthorityEditText.setText(passportAuthority)
        passportIdEditText.setText(passportNumber)
        iinEditText.setText(tin)
        showFrontPassportInfo(frontImageUploaded)
        showBackPassportInfo(backImageUploaded)
    }

    override fun onUpdateSuccess() {
        popScreen()
    }

    override fun showPassportProgress(isLoading: Boolean, passportSide: PassportSide) {
        frontProgressBar.isVisible = isLoading && passportSide == PassportSide.Front
        backProgressBar.isVisible = isLoading && passportSide == PassportSide.Back
    }

    override fun setButtonEnabled(isEnabled: Boolean) {
        progressButton.isEnabled = isEnabled
    }

    override fun onItemsSelected(result: Uri) {
        presenter.onImageSelected(result.mediaItems.firstOrNull())
    }
}*/
