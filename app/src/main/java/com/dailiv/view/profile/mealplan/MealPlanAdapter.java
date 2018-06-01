package com.dailiv.view.profile.mealplan;

import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.annimon.stream.IntStream;
import com.annimon.stream.function.Consumer;
import com.annimon.stream.function.Function;
import com.dailiv.R;
import com.dailiv.internal.data.local.pojo.MealPlan;

import java.util.ArrayList;
import java.util.List;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import rx.functions.Action1;

import static com.dailiv.util.common.CollectionUtil.mapListToList;

/**
 * Created by aldo on 5/27/18.
 */

@RequiredArgsConstructor
public class MealPlanAdapter extends RecyclerView.Adapter<MealPlanAdapterViewHolder>{

    @Setter
    private List<MealPlan> mealPlans = new ArrayList<>();

    @NonNull
    private Action1<String> navigateToRecipe;

    @Override
    public MealPlanAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal_plan, parent, false);
        return new MealPlanAdapterViewHolder(v);

    }

    @Override
    public void onBindViewHolder(MealPlanAdapterViewHolder holder, int position) {

        holder.getTvDay().setText(mealPlans.get(holder.getAdapterPosition()).getDay());

        recipeApplyText(holder.getTvBreakfast(), MealPlan::getBreakfast).accept(holder.getAdapterPosition());

        recipeApplyText(holder.getTvLunch(), MealPlan::getLunch).accept(holder.getAdapterPosition());

        recipeApplyText(holder.getTvDinner(), MealPlan::getDinner).accept(holder.getAdapterPosition());

        recipeApplyText(holder.getTvSnack(), MealPlan::getSnack).accept(holder.getAdapterPosition());

    }

    private Consumer<Integer> recipeApplyText(TextView textView, Function<MealPlan, List<MealPlan.MealPlanRecipe>> mapper) {

        return i -> {
            MealPlan mealPlan = mealPlans.get(i);
            textView.setText(mealPlanSpannableFunc().apply(mapper.apply(mealPlan)));
            textView.setMovementMethod(LinkMovementMethod.getInstance());
        };
    }

    private Function<List<MealPlan.MealPlanRecipe>, SpannableString> mealPlanSpannableFunc() {

        return list -> {
            List<String> recipeNames = mapListToList(list, MealPlan.MealPlanRecipe::getName);

            if(list.isEmpty()) {
                return new SpannableString("-");
            }

            String recipeNameJoined = TextUtils.join(", ", recipeNames);

            SpannableString ss = new SpannableString(recipeNameJoined);

            List<Pair<Integer, Integer>> pairIndexList = getPairIndex(recipeNames);

            IntStream.of(0, list.size() - 1)
                    .forEach(i -> {

                        MealPlan.MealPlanRecipe mealPlanRecipe = list.get(i);

                        Pair<Integer, Integer> pairIndex = pairIndexList.get(i);

                        ClickableSpan span = new ClickableSpan() {
                            @Override
                            public void onClick(View view) {
                                navigateToRecipe.call(mealPlanRecipe.getSlug());
                            }

                            @Override
                            public void updateDrawState(TextPaint ds) {
                                super.updateDrawState(ds);
                                ds.setUnderlineText(false);
                            }
                        };

                        ss.setSpan(span, pairIndex.first, pairIndex.second + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    });

            return ss;
        };
    }

    private List<Pair<Integer, Integer>> getPairIndex(List<String> list) {
        List<Pair<Integer, Integer>> pairIndex = new ArrayList<>();

        for(int i = 0 ; i < list.size(); i++) {

            int len = list.get(i).length();
            if(i == 0){

                pairIndex.add(Pair.create(0, len - 1));
            }

            else{

                int first = pairIndex.get(i - 1).second + 3;
                pairIndex.add(Pair.create(first, first + len - 1));
            }

        }

        return pairIndex;

    }

    @Override
    public int getItemCount() {
        return mealPlans.size();
    }
}
