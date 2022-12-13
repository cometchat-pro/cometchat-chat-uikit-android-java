package com.cometchatworkspace.components.messages.common.extensions;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.exceptions.CometChatException;
import com.cometchat.pro.models.BaseMessage;
import com.cometchat.pro.models.TextMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import com.cometchat.pro.models.User;
import com.cometchatworkspace.components.messages.common.extensions.Collaborative.CometChatWebViewActivity;
import com.cometchatworkspace.components.shared.secondaryComponents.cometchatReaction.ReactionUtils;
import com.cometchatworkspace.components.shared.secondaryComponents.cometchatReaction.model.Reaction;
import com.cometchatworkspace.components.shared.secondaryComponents.cometchatStickers.model.Sticker;
import com.cometchatworkspace.resources.constants.UIKitConstants;
import com.cometchatworkspace.resources.utils.CometChatError;

public class Extensions {

    private static final String TAG = "Extensions";

    public static final String linkPreview = "link-preview";
    public static final String smartReply = "smart-reply";
    public static final String messageTranslation = "message-translation";
    public static final String profanityFilter = "profanity-filter";
    public static final String imageModeration = "image-moderation";
    public static final String thumbnailGeneration = "thumbnail-generator";
    public static final String sentimentalAnalysis = "sentiment-analysis";
    public static final String polls = "polls";
    public static final String reactions = "reactions";
    public static final String whiteboard = "whiteboard";
    public static final String document = "document";
    public static final String dataMasking = "data-masking";
    public static final String stickers = "stickers";
    public static final String saveMessage = "save-message";
    public static final String pinMessage = "pin-message";
    public static final String voiceTranscription = "voice-transcription";
    public static final String richMedia = "rich-media";
    public static final String malwareScanner = "virus-malware-scanner";
    public static final String mentions = "mentions";

    public static boolean getImageModeration(Context context, BaseMessage baseMessage) {
        boolean result = false;
        try {
            HashMap<String, JSONObject> extensionList = extensionCheck(baseMessage);
            if (extensionList != null && extensionList.containsKey("imageModeration")) {
                JSONObject imageModeration = extensionList.get("imageModeration");
                if (imageModeration.has("unsafe")) {
                    String unsafe = imageModeration.getString("unsafe");
                    result = unsafe.equals("yes");
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Below method is used to get a thumbnail generated by thumbnail generation extension.
     * It takes baseMessage as a parameter and check metadata of it. It check whether it contains
     * Thumbnail Generation metadata or not if yes then it will take 'url_small' from its response
     * and return it as result;
     *
     * @param context
     * @param baseMessage object of BaseMessage
     * @return is a String which contains url_small
     */
    public static String getThumbnailGeneration(Context context, BaseMessage baseMessage) {
        String resultUrl = null;
        try {
            HashMap<String, JSONObject> extensionList = extensionCheck(baseMessage);
            if (extensionList != null && extensionList.containsKey("thumbnailGeneration")) {
                JSONObject thumbnailGeneration = extensionList.get("thumbnailGeneration");
                resultUrl = thumbnailGeneration.getString("url_small");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return resultUrl;
    }


    public static List<String> getSmartReplyList(BaseMessage baseMessage){
        HashMap<String, JSONObject> extensionList = extensionCheck(baseMessage);
        List<String> replyList = new ArrayList<>();
        if (extensionList != null && extensionList.containsKey("smartReply")) {
            JSONObject replyObject = extensionList.get("smartReply");
            try {
                replyList.add(replyObject.getString("reply_positive"));
                replyList.add(replyObject.getString("reply_neutral"));
                replyList.add(replyObject.getString("reply_negative"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return replyList;
    }


    public static HashMap<String,JSONObject> extensionCheck(BaseMessage baseMessage)
    {
        JSONObject metadata = baseMessage.getMetadata();
        HashMap<String,JSONObject> extensionMap = new HashMap<>();
        try {
            if (metadata != null) {
                JSONObject injectedObject = metadata.getJSONObject("@injected");
                if (injectedObject != null && injectedObject.has("extensions")) {
                    JSONObject extensionsObject = injectedObject.getJSONObject("extensions");
                    if (extensionsObject != null && extensionsObject.has(linkPreview)) {
                        JSONObject linkPreviewObject = extensionsObject.getJSONObject(linkPreview);
                        JSONArray linkPreview = linkPreviewObject.getJSONArray("links");
                        if (linkPreview.length() > 0) {
                            extensionMap.put("linkPreview",linkPreview.getJSONObject(0));
                        }

                    }
                    if (extensionsObject !=null && extensionsObject.has(smartReply)) {
                        extensionMap.put("smartReply",extensionsObject.getJSONObject(smartReply));
                    }
                    if (extensionsObject!=null && extensionsObject.has(messageTranslation)) {
                        extensionMap.put("messageTranslation",extensionsObject.getJSONObject(messageTranslation));
                    }
                    if (extensionsObject!=null && extensionsObject.has(profanityFilter)) {
                        extensionMap.put("profanityFilter",extensionsObject.getJSONObject(profanityFilter));
                    }
                    if (extensionsObject!=null && extensionsObject.has(imageModeration)) {
                        extensionMap.put("imageModeration",extensionsObject.getJSONObject(imageModeration));
                    }
                    if (extensionsObject!=null && extensionsObject.has(thumbnailGeneration)) {
                        extensionMap.put("thumbnailGeneration",extensionsObject.getJSONObject(thumbnailGeneration));
                    }
                    if (extensionsObject!=null && extensionsObject.has(sentimentalAnalysis)) {
                        extensionMap.put("sentimentAnalysis",extensionsObject.getJSONObject(sentimentalAnalysis));
                    }
                    if (extensionsObject!=null && extensionsObject.has(polls)) {
                        extensionMap.put("polls",extensionsObject.getJSONObject(polls));
                    }
                    if (extensionsObject!=null && extensionsObject.has(reactions)) {
                        if (extensionsObject.get(reactions) instanceof JSONObject)
                            extensionMap.put("reactions",extensionsObject.getJSONObject(reactions));
                    }
                    if (extensionsObject!=null && extensionsObject.has(whiteboard)) {
                        extensionMap.put("whiteboard",extensionsObject.getJSONObject(whiteboard));
                    }
                    if (extensionsObject!=null && extensionsObject.has(document)) {
                        extensionMap.put("document",extensionsObject.getJSONObject(document));
                    }
                    if (extensionsObject!=null && extensionsObject.has(dataMasking)) {
                        extensionMap.put("dataMasking",extensionsObject.getJSONObject(dataMasking));
                    }
                }
                return extensionMap;
            }
            else
                return null;
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean checkSentiment(BaseMessage baseMessage) {
        boolean result = false;
        HashMap<String,JSONObject> extensionList = extensionCheck(baseMessage);
        try {
            if (extensionList.containsKey("sentimentAnalysis")) {
                JSONObject sentimentAnalysis = extensionList.get("sentimentAnalysis");
                String str = sentimentAnalysis.getString("sentiment");
                result = str.equals("negative");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Below method checks whether baseMessage contains profanity or not using Profanity Filter
     * extension. If yes then it will return clean message else the orignal message will be return.
     * @param baseMessage is object of BaseMessage
     * @return a String value.
     */
    public static String checkProfanityMessage(Context context,BaseMessage baseMessage) {
        String result = ((TextMessage)baseMessage).getText();
        HashMap<String,JSONObject> extensionList = Extensions.extensionCheck(baseMessage);
        if (extensionList!=null) {
            try {
                if (extensionList.containsKey("profanityFilter")) {
                    JSONObject profanityFilter = extensionList.get("profanityFilter");
                    String profanity = profanityFilter.getString("profanity");
                    String cleanMessage = profanityFilter.getString("message_clean");
                    if (profanity.equals("no"))
                        result = ((TextMessage)baseMessage).getText();
                    else
                        result = cleanMessage;
                } else {
                    result = ((TextMessage)baseMessage).getText().trim();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String checkDataMasking(Context context,BaseMessage baseMessage){
        String result = ((TextMessage)baseMessage).getText();
        String sensitiveData;
        String messageMasked;
        HashMap<String, JSONObject> extensionList = Extensions.extensionCheck(baseMessage);
        if (extensionList != null){
            try {
                if (extensionList.containsKey("dataMasking")){
                    JSONObject dataMasking = extensionList.get("dataMasking");
                    JSONObject dataObject = dataMasking.getJSONObject("data");
                    if (dataObject.has("sensitive_data") && dataObject.has("message_masked")){
                        sensitiveData = dataObject.getString("sensitive_data");
                        messageMasked = dataObject.getString("message_masked");
                        if (sensitiveData.equals("no"))
                            result = ((TextMessage)baseMessage).getText();
                        else
                            result = messageMasked;
                    }
                    else if (dataObject.has("action") && dataObject.has("message")){
                        result = dataObject.getString("message");
                    }
                }
                else {
                    result = ((TextMessage)baseMessage).getText();
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    public static int userVotedOn(BaseMessage baseMessage,int totalOptions,String loggedInUserId) {
        int result = 0;
        JSONObject resultJson = getPollsResult(baseMessage);
        try {
            if (resultJson.has("options")) {
                JSONObject options = resultJson.getJSONObject("options");
                for (int k = 0; k <totalOptions; k++) {
                    JSONObject option = options.getJSONObject(String.valueOf(k+1));
                    if (option.has("voters") && option.get("voters") instanceof JSONObject) {
                        JSONObject voterList = option.getJSONObject("voters");
                        if (voterList.has(loggedInUserId)) {
                                result = k+1;
                        }
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public static int getVoteCount(BaseMessage baseMessage) {
        int voteCount = 0;
        JSONObject result = getPollsResult(baseMessage);
        try {

            if (result.has("total")) {
                voteCount = result.getInt("total");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return voteCount;
    }
    public static JSONObject getPollsResult(BaseMessage baseMessage) {
        JSONObject result = new JSONObject();
        HashMap<String, JSONObject> extensionList = Extensions.extensionCheck(baseMessage);
        if (extensionList != null) {
            try {
                if (extensionList.containsKey("polls")) {
                    JSONObject polls = extensionList.get("polls");
                    if (polls.has("results")) {
                        result = polls.getJSONObject("results");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    public static ArrayList<String> getVoterInfo(BaseMessage baseMessage,int totalOptions) {
        ArrayList<String> votes = new ArrayList<>();
        JSONObject result = getPollsResult(baseMessage);
        try {
            if (result.has("options")) {
                JSONObject options = result.getJSONObject("options");
                for (int k=0;k<totalOptions;k++) {
                    JSONObject optionK = options.getJSONObject(String.valueOf(k+1));
                    votes.add(optionK.getString("count"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return votes;
    }

    public static void fetchStickers(ExtensionResponseListener extensionResponseListener) {
        if(CometChat.isExtensionEnabled(stickers)) {
            CometChat.callExtension("stickers", "GET", "/v1/fetch", null, new CometChat.CallbackListener<JSONObject>() {
                @Override
                public void onSuccess(JSONObject jsonObject) {
                    extensionResponseListener.OnResponseSuccess(jsonObject);
                }

                @Override
                public void onError(CometChatException e) {
                    extensionResponseListener.OnResponseFailed(new CometChatException(e.getCode(),
                            CometChatError.Extension.localized(e,"stickers")));
                }
            });
        } else {
            extensionResponseListener.OnResponseFailed(new CometChatException("ERR_EXTENSION_NOT_ENABLED",
                    "Enable the extension from CometChat Pro dashboard","stickers"));
        }
    }

    public static HashMap<String,List<Sticker>> extractStickersFromJSON(JSONObject jsonObject) {
        List<Sticker> stickers = new ArrayList<>();
        if (jsonObject != null) {
            try {
                JSONObject dataObject = jsonObject.getJSONObject("data");
                JSONArray defaultStickersArray = dataObject.getJSONArray("defaultStickers");
                Log.d(TAG, "getStickersList: defaultStickersArray "+defaultStickersArray.length());
                for (int i = 0; i < defaultStickersArray.length(); i++) {
                    JSONObject stickerObject = defaultStickersArray.getJSONObject(i);
                    String stickerOrder = stickerObject.getString("stickerOrder");
                    String stickerSetId = stickerObject.getString("stickerSetId");
                    String stickerUrl = stickerObject.getString("stickerUrl");
                    String stickerSetName = stickerObject.getString("stickerSetName");
                    String stickerName = stickerObject.getString("stickerName");
                    Sticker sticker = new Sticker(stickerName,stickerUrl,stickerSetName);
                    stickers.add(sticker);
                }
                if (dataObject.has("customStickers")) {
                    JSONArray customSticker = dataObject.getJSONArray("customStickers");
                    Log.d(TAG, "getStickersList: customStickersArray " + customSticker);
                    for (int i = 0; i < customSticker.length(); i++) {
                        JSONObject stickerObject = customSticker.getJSONObject(i);
                        String stickerOrder = stickerObject.getString("stickerOrder");
                        String stickerSetId = stickerObject.getString("stickerSetId");
                        String stickerUrl = stickerObject.getString("stickerUrl");
                        String stickerSetName = stickerObject.getString("stickerSetName");
                        String stickerName = stickerObject.getString("stickerName");
                        Sticker sticker = new Sticker(stickerName, stickerUrl, stickerSetName);
                        stickers.add(sticker);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        HashMap<String,List<Sticker>> stickerMap = new HashMap();
        for (int i=0;i<stickers.size();i++) {
            if (stickerMap.containsKey(stickers.get(i).getSetName())) {
                stickerMap.get(stickers.get(i).getSetName()).add(stickers.get(i));
            } else {
                List<Sticker> list = new ArrayList<>();
                list.add(stickers.get(i));
                stickerMap.put(stickers.get(i).getSetName(),list);
            }
        }
        return stickerMap;
    }

    public static List<Reaction> getInitialReactions(int i) {
        List<Reaction> randomReaction = new ArrayList<>();
        List<Reaction> fetchedReaction = ReactionUtils.getFeelList();
        for (int k=0;k<i;k++) {
            Reaction reaction = fetchedReaction.get(k);
            randomReaction.add(reaction);
        }
        return randomReaction;
    }

    public static HashMap<String, String> getReactionsOnMessage(BaseMessage baseMessage) {
        HashMap<String,String> result = new HashMap<>();
        HashMap<String,JSONObject> extensionList = Extensions.extensionCheck(baseMessage);
        if (extensionList!=null) {
            try {
                if (extensionList.containsKey("reactions")) {
                    JSONObject data = extensionList.get("reactions");
                    Iterator<String> keys= data.keys();
                    while (keys.hasNext())
                    {
                        String keyValue = (String)keys.next();
                        JSONObject react = data.getJSONObject(keyValue);
                        String reactCount = react.length()+"";
                        result.put(keyValue,reactCount);
                    }
                }
            }catch (Exception e) { e.printStackTrace(); }
        }
        return result;
    }

    public static HashMap<String, List<String>> getReactionsInfo(JSONObject jsonObject) {
        HashMap<String,List<String>> result = new HashMap<>();
        if (jsonObject!=null) {
            try {
                JSONObject injectedObject = jsonObject.getJSONObject("@injected");
                if (injectedObject != null && injectedObject.has("extensions")) {
                    JSONObject extensionsObject = injectedObject.getJSONObject("extensions");
                    if (extensionsObject.has("reactions")) {
                        JSONObject data = extensionsObject.getJSONObject("reactions");
                        Iterator<String> keys = data.keys();
                        while (keys.hasNext()) {
                            List<String> reactionUser = new ArrayList<>();
                            String keyValue = (String) keys.next();
                            JSONObject react = data.getJSONObject(keyValue);
                            Iterator<String> uids = react.keys();
                            while (uids.hasNext()) {
                                String uid = (String) uids.next();
                                JSONObject user = react.getJSONObject(uid);
                                reactionUser.add(user.getString("name"));
                            }
                            result.put(keyValue, reactionUser);
                        }
                    }
                }
            }catch (Exception e) {
                e.printStackTrace(); }
        }
        return result;
    }

    public static void callWriteBoardExtension(String receiverId,String receiverType,ExtensionResponseListener extensionResponseListener) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("receiver", receiverId);
            jsonObject.put("receiverType", receiverType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        CometChat.callExtension("document", "POST", "/v1/create", jsonObject, new CometChat.CallbackListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                extensionResponseListener.OnResponseSuccess(jsonObject);
            }
            @Override
            public void onError(CometChatException e) {
                extensionResponseListener.OnResponseFailed(e);
            }
        });
    }

    public static void callWhiteBoardExtension(String receiverId,String receiverType,ExtensionResponseListener extensionResponseListener) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("receiver", receiverId);
            jsonObject.put("receiverType", receiverType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        CometChat.callExtension("whiteboard", "POST", "/v1/create", jsonObject, new CometChat.CallbackListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                extensionResponseListener.OnResponseSuccess(jsonObject);
            }
            @Override
            public void onError(CometChatException e) {
                extensionResponseListener.OnResponseFailed(e);
            }
        });
    }

    public static String getWhiteBoardUrl(BaseMessage baseMessage) {
        String boardUrl ="";
        HashMap<String, JSONObject> extensionCheck = extensionCheck(baseMessage);
        if (extensionCheck.containsKey("whiteboard")) {
            JSONObject whiteBoardData = extensionCheck.get("whiteboard");
            if (whiteBoardData.has("board_url")) {
                try {
                    boardUrl = whiteBoardData.getString("board_url");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return boardUrl;
    }

    public static String getWriteBoardUrl(BaseMessage baseMessage) {
        String boardUrl ="";
        HashMap<String, JSONObject> extensionCheck = extensionCheck(baseMessage);
        if (extensionCheck.containsKey("document")) {
            JSONObject whiteBoardData = extensionCheck.get("document");
            if (whiteBoardData.has("document_url")) {
                try {
                    boardUrl = whiteBoardData.getString("document_url");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return boardUrl;
    }


    public static void openWhiteBoard(BaseMessage baseMessage, Context context) {
        try {
            String boardUrl = "";
            HashMap<String, JSONObject> extensionCheck = extensionCheck(baseMessage);
            if (extensionCheck.containsKey("whiteboard")) {
                JSONObject whiteBoardData = extensionCheck.get("whiteboard");
                if (whiteBoardData.has("board_url")) {
                    boardUrl = whiteBoardData.getString("board_url");
                    String userName = CometChat.getLoggedInUser().getName().replace("//s+","_");
                    boardUrl = boardUrl+"&username="+userName;
                    Intent intent = new Intent(context, CometChatWebViewActivity.class);
                    intent.putExtra(UIKitConstants.IntentStrings.URL, boardUrl);
                    context.startActivity(intent);
                }
            }
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public static void openWriteBoard(BaseMessage baseMessage, Context context) {
        try {
            String boardUrl = "";
            HashMap<String, JSONObject> extensionCheck = extensionCheck(baseMessage);
            if (extensionCheck.containsKey("document")) {
                JSONObject whiteBoardData = extensionCheck.get("document");
                if (whiteBoardData.has("document_url")) {
                    boardUrl = whiteBoardData.getString("document_url");
                    Intent intent = new Intent(context, CometChatWebViewActivity.class);
                    intent.putExtra(UIKitConstants.IntentStrings.URL, boardUrl);
                    context.startActivity(intent);
                }
            }
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    public static String getTranslatedMessage(BaseMessage baseMessage) {
        String translatedMessage = ((TextMessage)baseMessage).getText();
        try {
            if (baseMessage.getMetadata() != null) {
                JSONObject metadataObject = baseMessage.getMetadata();
                if (metadataObject.has("values")) {
                    JSONObject valueObject = metadataObject.getJSONArray("values").getJSONObject(0);
                    if (valueObject.has("data")) {
                        JSONObject dataObject = valueObject.getJSONObject("data");
                        if (dataObject.has("translations")) {
                            JSONArray translations = dataObject.getJSONArray("translations");
                            if (translations.length() > 0) {
                                JSONObject jsonObject = translations.getJSONObject(0);
                                translatedMessage = jsonObject.getString("message_translated");
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return translatedMessage;
    }

    public static boolean isMessageTranslated(JSONObject jsonObject,String txtMessage) {
        boolean result = false;
        try {
            JSONObject metadataObject = jsonObject;
            if (metadataObject.has("data")) {
                JSONObject dataObject = metadataObject.getJSONObject("data");
                if (dataObject.has("translations")) {
                    JSONArray translations = dataObject.getJSONArray("translations");
                    if (translations.length() > 0) {
                        JSONObject translationsJSONObject = translations.getJSONObject(0);
                        String language = translationsJSONObject.getString("language_translated");
                        String localLanguage = Locale.getDefault().getLanguage();
                        String translatedMessage = translationsJSONObject.getString("message_translated");
                        result = language.equalsIgnoreCase(localLanguage) &&
                                !txtMessage.equalsIgnoreCase(translatedMessage);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getTextFromTranslatedMessage(JSONObject jsonObject, String originalString) {
        String result="";
        try {
            JSONObject metadataObject = jsonObject;
            if (metadataObject.has("data")) {
                JSONObject dataObject = metadataObject.getJSONObject("data");
                if (dataObject.has("translations")) {
                    JSONArray translations = dataObject.getJSONArray("translations");
                    if (translations.length() > 0) {
                        JSONObject translationsJSONObject = translations.getJSONObject(0);
                        String language = translationsJSONObject.getString("language_translated");
                        String localLanguage = Locale.getDefault().getLanguage();
                        String translatedMessage = translationsJSONObject.getString("message_translated");
                        if (language.equalsIgnoreCase(localLanguage) &&
                                !originalString.equalsIgnoreCase(translatedMessage)) {
                            result = originalString+"\n("+translatedMessage+")";
                        } else {
                            result = originalString;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<String> translateSmartReplyMessage(BaseMessage baseMessage,JSONObject replyObject,
                                                    ExtensionResponseListener extensionResponseListener) {
        List<String> resultList = new ArrayList<>();
        try {
            for (int i = 0;i<replyObject.length();i++) {
                String localeLanguage = Locale.getDefault().getLanguage();
                JSONObject body = new JSONObject();
                JSONArray languages = new JSONArray();
                languages.put(localeLanguage);
                body.put("msgId", baseMessage.getId());
                body.put("languages", languages);
                if (i==0)
                    body.put("text", replyObject.getString("reply_positive"));
                else if (i==1)
                    body.put("text",replyObject.getString("reply_neutral"));
                else
                    body.put("text",replyObject.getString("reply_negative"));

                final String str = body.getString("text");
                CometChat.callExtension("message-translation", "POST",
                        "/v2/translate", body, new CometChat.CallbackListener<JSONObject>() {
                            @Override
                            public void onSuccess(JSONObject jsonObject) {
                                if (Extensions.isMessageTranslated(jsonObject,str)) {
                                    String translatedMessage = Extensions
                                            .getTextFromTranslatedMessage(jsonObject,
                                                    str);
                                    resultList.add(translatedMessage);
                                }
                            }

                            @Override
                            public void onError(CometChatException e) {
                            }
                        });
            }
            extensionResponseListener.OnResponseSuccess(resultList);
        } catch (Exception e) {
            extensionResponseListener.OnResponseFailed(new CometChatException("ERR_TRANSLATION_FAILED","Not able to translate smart reply"));
        }
        return resultList;
    }



    public static void sendReactionOptimistic(Reaction reaction,BaseMessage baseMessage) throws JSONException {
        User loggedInUser = CometChat.getLoggedInUser();
        if (baseMessage!=null) {
            JSONObject reactionJSON = new JSONObject();
            reactionJSON.put("name",loggedInUser.getName());
            reactionJSON.put("avatar",loggedInUser.getAvatar());

            JSONObject listOfReaction = new JSONObject();
            listOfReaction.put(loggedInUser.getUid(),reactionJSON);

            JSONObject reactionsObject = new JSONObject();
            reactionsObject.put(reaction.getName(),listOfReaction);

            JSONObject extensions = new JSONObject();
            extensions.put(reactions,reactionsObject);

            JSONObject injected = new JSONObject();
            injected.put("extensions",extensions);

            JSONObject metadata = new JSONObject();

            if (baseMessage.getMetadata()!=null) {
                if (baseMessage.getMetadata().has("@injected")) {
                    injected = baseMessage.getMetadata().getJSONObject("@injected");
                    if (injected.has("extensions")) {
                        extensions = injected.getJSONObject("extensions");
                        if (extensions.has(reactions)) {
                            reactionsObject = extensions.getJSONObject(reactions);
                            for(int i=0;i<reactionsObject.length();i++) {
                                reactionJSON = reactionsObject.getJSONObject(reaction.getName());
                                if(reactionJSON.has(loggedInUser.getUid())) {
                                    reactionJSON.remove(loggedInUser.getUid());
                                } else {
                                    reactionJSON.accumulate(loggedInUser.getUid(),loggedInUser.toJson());
                                }
                            }
                            if (reactionJSON.length()==0)
                                listOfReaction.remove(reaction.getName());

                        } else {
                            extensions.accumulate(reactions,reactionsObject);
                        }
                    } else {
                        injected.accumulate("extensions",extensions);
                    }
                } else {
                    metadata = baseMessage.getMetadata();
                }
            } else {
                metadata.put("@injected",injected);
            }
            metadata.accumulate("@injected",injected);
            baseMessage.setMetadata(metadata);
        }
    }

    public static boolean isReactedByLoggedInUser(JSONObject data, String str) {
        boolean result=false;
        HashMap<String, List<String>> reactionInfo = Extensions.getReactionsInfo(data);
        List<String> reactionUsers = reactionInfo.get(str);
        if (reactionUsers!=null) {
            for (String uid : reactionUsers) {
                if (uid.equalsIgnoreCase(CometChat.getLoggedInUser().getName())) {
                    result = true;
                    break;
                } else
                    result = false;
            }
        }
        return result;
    }
}
