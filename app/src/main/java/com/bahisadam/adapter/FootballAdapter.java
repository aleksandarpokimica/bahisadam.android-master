package com.bahisadam.adapter;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.sectionedrecyclerview.SectionedRecyclerViewAdapter;
import com.bahisadam.Cache;
import com.bahisadam.MyApplication;
import com.bahisadam.R;
import com.bahisadam.fragment.FootballFragment;
import com.bahisadam.interfaces.Constant;
import com.bahisadam.model.LeagueMatchList;
import com.bahisadam.model.MatchPOJO;
import com.bahisadam.utility.FontManager;
import com.bahisadam.utility.Utilities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.bahisadam.view.HomeActivity;

/**
 * Created by atata on 26/12/2016.
 * Football Adapter
 */

public class FootballAdapter extends SectionedRecyclerViewAdapter<RecyclerView.ViewHolder> implements Constant {

    public static final float PROPORTION = 1.2f;
    public List<LeagueMatchList> data;
    public List<LeagueMatchList> dataToShow;
    private final ArrayMap<Integer, Integer> mHeaderLocationMap;
    private Activity activity;
    private FootballFragment frag;
    private OnMatchClickListener mListener;
    public boolean toggled;

    public FootballAdapter(List<LeagueMatchList> data, Activity activity, FootballFragment frag) {
        this.data = data;
        this.dataToShow = data;

        mHeaderLocationMap = new ArrayMap<>();
        this.activity = activity;
        this.frag = frag;
        toggled = false;
        ((HomeActivity)activity).setOnQueryTextListener(new HomeActivity.OnQueryTextListener() {
            @Override
            public void onQueryChanged(String query) {
                filter(query);
            }
        });
    }


    public int getSection(int absolutePath){
        return mHeaderLocationMap.get(absolutePath);
    }

    @Override
    public int getSectionCount() {
        return dataToShow.size();
    }

    @Override
    public int getItemCount(int section) {
        return dataToShow.get(section).getData().size();
    }

    public Activity getActivity() {
        return activity;
    }

    private Bitmap getBitmapCountry(int id){

        Bitmap bitmap = Cache.getBitmap("Country"+id);
        if(bitmap == null){
            try {
                Drawable dr;

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    dr = getActivity().getResources().getDrawable(id, getActivity().getTheme());
                } else {
                    //noinspection deprecation
                    dr = getActivity().getResources().getDrawable(id);
                }
                Bitmap bmp = Bitmap.createBitmap(200,
                        150, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bmp);
                dr.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                dr.draw(canvas);
                bitmap= Utilities.getRoundedCornerBitmap(bmp,Utilities.getPx(getActivity(),0));
                Cache.addBitmap("Country"+id,bitmap);

            } catch (Resources.NotFoundException e){
                Log.e("Resourcen not found", e.getMessage());
            }
        }
        return bitmap;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder headerHolder, final int section) {
        final HeaderVH holder = (HeaderVH) headerHolder;
        String leagueName = dataToShow.get(section).getLeauge().getLeagueNameTr();
        toggled = dataToShow.get(section).getLeauge().getToggled();
        holder.sectionTitle.setText(leagueName);
        Integer total = dataToShow.get(section).getData().size();
        holder.totalMatch.setText(total.toString());
        final FootballAdapter adp = this;

        if(toggled) {
            holder.totalMatch.setVisibility(View.VISIBLE);
            holder.rightArrow.setVisibility(View.GONE);
        } else {
            holder.totalMatch.setVisibility(View.GONE);
            holder.rightArrow.setVisibility(View.VISIBLE);
        }

        final String countryCode = dataToShow.get(section).get(0).getCountry().getCountryCode().replace('-', '_');
        Activity ctx = getActivity();
        int id = ctx.getResources().getIdentifier(countryCode,"drawable",ctx.getPackageName());
        if(id != 0 ) {
            Bitmap bitmap = getBitmapCountry(id);
            if(bitmap != null){
                holder.img_title_logo_img.setImageBitmap(bitmap);
            }
        }

        holder.rootLaout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() != R.id.right_arrow) {
                    boolean toogled = !dataToShow.get(section).getLeauge().getToggled();
                    dataToShow.get(section).getLeauge().setToggled(toogled);
                    adp.notifyDataSetChanged();
                }

            }
        });

        Typeface tf = FontManager.getTypeface(activity, FontManager.FONTAWESOME);
        holder.rightArrow.setTypeface(tf);
        holder.leagueDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.league_detail) {
                    MatchPOJO.LeagueId league = dataToShow.get(section).getLeauge();

                    Utilities.openLeagueDetails(getActivity(),
                            league.getId(),
                            league.getLeagueNameTr(),
                            countryCode);
                }
            }
        });
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder itemHolder, int section, int relativePosition, int absolutePosition) {
        Log.d("debug", "section number is" + section);
        final ItemVH holder = (ItemVH) itemHolder;
        final MatchPOJO.Match currentMatch = dataToShow.get(section).get(relativePosition);
        MatchPOJO.HomeTeam homeTeam = currentMatch.getHomeTeam();
        MatchPOJO.AwayTeam awayTeam = currentMatch.getAwayTeam();

        boolean toggled = dataToShow.get(section).getLeauge().getToggled();
        if(toggled) {
            if(holder.match_list_container.getVisibility() == View.VISIBLE) {
                holder.match_list_container.setVisibility(View.GONE);
            }
        } else {
            holder.match_list_container.setVisibility(View.VISIBLE);
        }

        if(homeTeam != null && awayTeam != null ) {
            holder.homeTeam.setText(homeTeam.getTeamNameTr());
            holder.awayTeam.setText(awayTeam.getTeamNameTr());
            if(MyApplication.sUse_Logo) {
                Utilities.loadLogoToView(holder.homeTeamLogo,
                        currentMatch.getHomeTeam().getId());
                Utilities.loadLogoToView(holder.awayTeamLogo,
                        currentMatch.getAwayTeam().getId());
            }
            else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                String color1 = currentMatch.getHomeTeam().getColor1();
                String color2 = currentMatch.getHomeTeam().getColor2();
                frag.loadBitmatIntoView(holder.homeTeamLogo,color1,color2);

                color1 = currentMatch.getAwayTeam().getColor1();
                color2 = currentMatch.getAwayTeam().getColor2();
                frag.loadBitmatIntoView(holder.awayTeamLogo,color1,color2);
            }
        }

        holder.itemSection = section;
        holder.relativePosition = relativePosition;
        holder.absolutePosition = absolutePosition;
        holder.code.setText(currentMatch.iddaa_code);
        if(currentMatch.iddaa_code == null) {
            holder.code.setText("");
        } else {
            holder.code.setText(currentMatch.iddaa_code);
        }
        refreshViews(holder);

        switch (currentMatch.getResultType()) {
            case CANCELLED:
                holder.details.setText(R.string.statusCanceled);
                holder.statusImage.setVisibility(View.VISIBLE);
                holder.statusImage.setImageResource(R.drawable.ic_cancelled);
                holder.scoreLayout.setVisibility(View.GONE);
                break;
            case POSTPONED:
                holder.details.setText(R.string.postponed);
                holder.statusImage.setVisibility(View.VISIBLE);
                holder.statusImage.setImageResource(R.drawable.ic_postponed);
                holder.scoreLayout.setVisibility(View.GONE);

                break;
            case INTERRUPTED:
                holder.details.setText(R.string.statusInterrupted);

                break;
            case DELAYED:
                holder.details.setText(R.string.statusDelayed);
                break;
            case PLAYED:
            case PLAYING:
                showScores(holder, currentMatch);
                updateScoreLayoutBackground(holder);
                if (currentMatch.getResultType().equals(PLAYED)) {
                    setupPlayedView(holder, currentMatch);
                } else {
                    setupPlayingView(holder, currentMatch);
                }
                break;
            case NOT_PLAYED:
                setupNotPlayed(holder, currentMatch);
                break;
        }


    }

    private void showScores(ItemVH holder, MatchPOJO.Match currentMatch) {
        if (currentMatch.getHomeGoals() != null && currentMatch.getAwayGoals() != null ){
            holder.scoreLayout.setVisibility(View.VISIBLE);
            holder.awayTeamScore.setVisibility(View.VISIBLE);
            holder.homeTeamScore.setVisibility(View.VISIBLE);
            holder.awayTeamScore.setText(String.format(Locale.US, "%d", currentMatch.getAwayGoals()));
            holder.homeTeamScore.setText(String.format(Locale.US, "%d", currentMatch.getHomeGoals()));
        }
    }

    private void updateScoreLayoutBackground(ItemVH holder) {
        holder.homeTeamScore.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        holder.awayTeamScore.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int width = Math.max(holder.homeTeamScore.getMeasuredWidth(), holder.awayTeamScore.getMeasuredWidth());
        holder.scoreLayout.getChildAt(0).getLayoutParams().width = width;
    }

    private void setupNotPlayed(ItemVH holder, MatchPOJO.Match currentMatch) {
        if (currentMatch.getMatchDate() != null) {
            Date d = Utilities.parseJSONDate(currentMatch.getMatchDate());
            String dateStr = Utilities.formatDate(d,"HH:mm");
            holder.details.setText(dateStr);
        }
        holder.favorite.setVisibility(View.VISIBLE);
        holder.homeTeamScore.setText("");
        holder.awayTeamScore.setText("");
        Utilities.paintFavoriteIconOntoWhiteBg(this.getActivity(), holder.favorite, currentMatch.getIsFavorite());
    }

    private void refreshViews(ItemVH holder) {
        holder.details.setVisibility(View.VISIBLE);
        holder.scoreLayout.setVisibility(View.GONE);
        holder.statusImage.setVisibility(View.GONE);
        holder.homeTeamScore.setVisibility(View.GONE);
        holder.awayTeamScore.setVisibility(View.GONE);
        holder.leftDetailsContainer.setBackgroundResource(R.color.grey_main);
        Typeface tf = FontManager.getTypeface(activity, FontManager.FONTAWESOME);
        holder.favorite.setTypeface(tf);
        holder.favorite.setVisibility(View.INVISIBLE);
        holder.halfTimeResults.setVisibility(View.GONE);
    }

    private void setupPlayingView(final ItemVH holder, MatchPOJO.Match currentMatch) {
        String detailsStr = "";
        try {
            String mins = "-";
            if (currentMatch.getIsHalfTime()) {
                mins = activity.getString(R.string.iv);
            } else {
                mins = currentMatch.getLiveMinute() + "'";
            }

            if(currentMatch.getLiveMinute() > 44 && currentMatch.getHalfTimeAwayScore() != null  && currentMatch.getHalfTimeHomeScore() != null) {
                holder.halfTimeResults.setVisibility(View.VISIBLE);
                detailsStr = "" + activity.getString(R.string.iv) + " " + currentMatch.getHalfTimeHomeScore() + ":" +  currentMatch.getHalfTimeAwayScore();
                holder.halfTimeResults.setTextColor(Color.parseColor("#FFFFFF"));
                holder.halfTimeResults.setText(detailsStr);
            } else {
                holder.halfTimeResults.setTextColor(Color.parseColor("#404040"));
            }

            Spannable text = new SpannableString(mins);
            text.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), 0, mins.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            text.setSpan(new ForegroundColorSpan(Color.WHITE),0, mins.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            text.setSpan(new RelativeSizeSpan(PROPORTION), 0, mins.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.details.setText(text);

            holder.layoutFavorite.setVisibility(View.VISIBLE);
            holder.favorite.setVisibility(View.VISIBLE);
            Utilities.paintFavoriteIconOntoWhiteBg(this.getActivity(), holder.favorite, currentMatch.getIsFavorite());
        } catch (NullPointerException e) {

            detailsStr = "0'\n" + detailsStr;
            e.printStackTrace();
        }

        holder.leftDetailsContainer.setBackgroundResource(R.color.live_background_color);
    }

    private void setupPlayedView(ItemVH holder, MatchPOJO.Match currentMatch) {
        String detailsStr;
        String ms = activity.getString(R.string.ms);
        detailsStr = "";
        holder.halfTimeResults.setVisibility(View.VISIBLE);
        if (currentMatch.getHalfTimeAwayScore() != null && currentMatch.getHalfTimeHomeScore() != null) {
            detailsStr = detailsStr + activity.getString(R.string.iv) + " " + currentMatch.getHalfTimeHomeScore() + ":" +
                    currentMatch.getHalfTimeAwayScore();
        }
        holder.details.setText(ms);
        holder.halfTimeResults.setText(detailsStr);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_HEADER){
            return new HeaderVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_section,parent,false));
        }else {
            return new ItemVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_page_item_redesign,parent,false));
        }
    }

    // Do Search...
    public void filter(final String text) {

        // Searching could be complex..so we will dispatch it to a different thread...
        new Thread(new Runnable() {
            @Override
            public void run() {
                dataToShow = new ArrayList<LeagueMatchList>();
                // Clear the filter list
                Locale locale = new Locale(MyApplication.sDefSystemLanguage);
                // If there is no search value, then add all original list items to filter list
                if (TextUtils.isEmpty(text)) {
                    dataToShow.addAll(data);
                } else {
                    // Iterate in the original List and add it to filter list...
                    for (LeagueMatchList item : data) {
                        LeagueMatchList filteredLeague = new LeagueMatchList(item.getLeauge());
                        for(MatchPOJO.Match match : item.getData()) {
                            if(match.iddaa_code != null && match.iddaa_code.contains(text)) {
                                filteredLeague.getData().add(match);
                            } else if((match.getHomeTeam() !=null && match.getAwayTeam() != null) && (match.getHomeTeam().getTeamNameTr().toLowerCase(locale).contains(text.toLowerCase(locale)) || match.getAwayTeam().getTeamNameTr().toLowerCase(locale).contains(text.toLowerCase(locale))) ) {
                                filteredLeague.getData().add(match);
                            } else if(match.getLeagueId() != null && match.getLeagueId().getLeagueNameTr().toLowerCase(locale).contains(text.toLowerCase(locale))) {
                                filteredLeague.getData().add(match);
                            } else if(match.getCountry() != null && match.getCountry().getCountryNameTr().toLowerCase(locale).contains(text.toLowerCase(locale))) {
                                filteredLeague.getData().add(match);
                            }
                        }

                        if(filteredLeague.getData().size() > 0) {
                            dataToShow.add(filteredLeague);
                        }
                    }
                }



                // Set on UI Thread
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Notify the List that the DataSet has changed...
                        notifyDataSetChanged();
                    }
                });

            }
        }).start();

    }

    public interface OnMatchClickListener {
        void onFavoriteClick(int section,int relativePosition,int absolutePosition);
        //void onIddaaClick(int section,int relativePosition);
        //void onCommentsClick(int section,int relativePosition);
        void onMatchClick(int section,int relativePosition);
    }

    public void setMatchClickListener(OnMatchClickListener listener ){
        mListener = listener;
    }

    class HeaderVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.img_title_logo_img) ImageView img_title_logo_img;
        @BindView(R.id.sectionTitle) TextView sectionTitle;
        @BindView(R.id.right_arrow) TextView rightArrow;
        @BindView(R.id.rootLayout) View rootLaout;
        @BindView(R.id.total_league_match) TextView totalMatch;
        @BindView(R.id.league_detail) LinearLayout leagueDetail;
        HeaderVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            rootLaout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener == null) return;
        }
    }

    class ItemVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.homeTeamLogo) ImageView homeTeamLogo;
        @BindView(R.id.awayTeamLogo) ImageView awayTeamLogo;
        @BindView(R.id.homeTeam) TextView homeTeam;
        @BindView(R.id.awayTeam) TextView awayTeam;
        @BindView(R.id.favorite) TextView favorite;
        @BindView(R.id.code) TextView code;
        @BindView(R.id.homeTeamScore) TextView homeTeamScore;
        @BindView(R.id.awayTeamScore) TextView awayTeamScore;
        @BindView(R.id.details) TextView details;
        @BindView(R.id.rootLayout) View rootLayout;
        @BindView(R.id.match_list_container) RelativeLayout match_list_container;
        @BindView(R.id.leftDetailsContainer) RelativeLayout leftDetailsContainer;
        @BindView(R.id.scoreLayout) ViewGroup scoreLayout;
        @BindView(R.id.img_status) ImageView statusImage;
        @BindView(R.id.layoutFavorite) RelativeLayout layoutFavorite;
        @BindView(R.id.halftimeResults) TextView halfTimeResults;
        int itemSection = -1;
        int relativePosition = -1;
        int absolutePosition = -1;

        ItemVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            rootLayout.setOnClickListener(this);
            favorite.setOnClickListener(this);
            layoutFavorite.setOnClickListener(this);
            this.setIsRecyclable(false);

        }

        @Override
        public void onClick(View v) {
            if(mListener ==null) return;

            try {
                switch (v.getId()) {
                    case R.id.rootLayout:
                        mListener.onMatchClick(itemSection, relativePosition);
                        break;
                    case R.id.layoutFavorite:
                    case R.id.favorite:
                        mListener.onFavoriteClick(itemSection, relativePosition, absolutePosition);
                        break;
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }
}
