package com.curso.onmessage.CustomAdapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.curso.onmessage.ImageFullscreen.ImageFullScreen_Main;
import com.curso.onmessage.R;
import org.apache.commons.io.FilenameUtils;
import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;


/**
 * Created by PC-PRAF on 16/10/2016.
 */

public class RecycleChatAdapter extends RecyclerView.Adapter<RecycleChatAdapter.ViewHolder> {

    private ArrayList<String[]> Messages;
    private Context context;
    private String ContactName;
    private String PathContact;
    private RelativeLayout.LayoutParams params1 = null;
    private static Boolean isCanceled = false;
    private static MediaPlayer mediaPlayer;
    private static final Handler handler = new Handler();
    private static int SeekPositionL = 0;
    private static int IsPlaying = -1;
    private RelativeLayout.LayoutParams params2 = null;
    private ArrayList<SeekBar> ASeekBar;
    private ArrayList<ImageView[]> ImagePlayControl;
    private ArrayList<TextView>TextViewRunTime;
    private static Runnable notification;
    private static String Pathx;
    private CreateMessageAudio createMessageAudio;
    private String behavior;

    public static void setMediaPlayer(MediaPlayer mediaPlayer) {
        RecycleChatAdapter.mediaPlayer = mediaPlayer;
    }
    public static MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
    public static Boolean getCanceled() {
        return isCanceled;
    }
    public static void setCanceled(Boolean canceled) {
        isCanceled = canceled;
    }
    public static void setIsPlaying(int isPlaying) {
        IsPlaying = isPlaying;
    }
   /* array[1] = 0 recived  ,
    * array[1] = 1 send     ,
    * array[2] = 1 text Message
    * array[2] = 2 image Message
    * array[2] = 3 video Message
    * array[2] = 4 audio Message
    * array[2] = 5 document Message */

    public RecycleChatAdapter(Context context, String ContactName, String ContactImagePath , String behavior) {
        this.behavior = behavior;
        this.ContactName = ContactName;
        this.PathContact = ContactImagePath;
        this.context = context;
        this.ASeekBar = new ArrayList<>();
        this.ImagePlayControl = new ArrayList<>();
        this.TextViewRunTime = new ArrayList<>();
        this.createMessageAudio = new CreateMessageAudio(this.context);

        Messages = new ArrayList<>();
        Messages.add(new String[]{"Hola adjlajsdlkj lkj klaklsdj lkajsdlk jalksj dlkjaslkdj lkajs kljalkj lkaj kaslkd jlkasjd lkjkls jaljsd lkjaslk jklajdslk asjdlka lkakds ljslkd ", "1", "1"});
        Messages.add(new String[]{"Hola Denilson...", "1", "1"});
        Messages.add(new String[]{Environment.getExternalStorageDirectory() + "/Message/Videos/video.mp4", "0", "3"});
        Messages.add(new String[]{Environment.getExternalStorageDirectory() + "/Message/Videos/video.mp4", "0", "3"});
        Messages.add(new String[]{Environment.getExternalStorageDirectory() + "/Message/Images/yc1.jpg", "0", "2"});
        Messages.add(new String[]{Environment.getExternalStorageDirectory() + "/Message/Images/yc2.jpg", "1", "2"});
        Messages.add(new String[]{Environment.getExternalStorageDirectory() + "/Message/Videos/video.mp4", "1", "3"});
        Messages.add(new String[]{Environment.getExternalStorageDirectory() + "/Message/Audios/audio1.ogg", "1", "4", "0"});
        Messages.add(new String[]{Environment.getExternalStorageDirectory() + "/Message/Audios/audio2.ogg", "0", "4", "0"});
        Messages.add(new String[]{Environment.getExternalStorageDirectory()+"/Message/Documents/Libro.xlsx", "0","5"});
        Messages.add(new String[]{Environment.getExternalStorageDirectory()+"/Message/Documents/Reunión.txt", "1","5"});
        Messages.add(new String[]{Environment.getExternalStorageDirectory()+"/Message/Documents/dedicatoria.docx", "1","5"});
        Messages.add(new String[]{Environment.getExternalStorageDirectory()+"/Message/Documents/dedicatoria.docx", "0","5"});
        Messages.add(new String[]{Environment.getExternalStorageDirectory()+"/Message/Documents/dedicatoria.wtf", "1","5"});
    }

    @Override
    public RecycleChatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_main, parent, false);
        RecycleChatAdapter.ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecycleChatAdapter.ViewHolder holder, final int position) {
        if (Messages.get(position)[1] == "0") {
            if (Messages.get(position)[2] == "1") {
                CreateMessageText(0, holder, position);
            } else if (Messages.get(position)[2] == "2" || Messages.get(position)[2] == "3") {
                CreateMessagePhotoVideo(0, holder, position);
            } else if(Messages.get(position)[2]=="4") {
                CreateMessageAudio(0, holder, position);
            }else{
                CreateMessageDocument(0,holder,position);
            }
        } else {
            if (Messages.get(position)[2] == "1") {
                CreateMessageText(1, holder, position);
            } else if (Messages.get(position)[2] == "2" || Messages.get(position)[2] == "3") {
                CreateMessagePhotoVideo(1, holder, position);
            } else if(Messages.get(position)[2]=="4") {
                CreateMessageAudio(1, holder, position);
            }else{
                CreateMessageDocument(1,holder,position);
            }
        }
    }

    @Override
    public int getItemCount() {
        return Messages.size();
    }


    private void CreateMessageText(int sendOrReciver, RecycleChatAdapter.ViewHolder holder, int position) {

        holder.linearLayoutMessageStructure.setVisibility(View.VISIBLE);
        holder.linearLayoutImageStructure.setVisibility(View.GONE);
        holder.video_play_duration.setVisibility(View.GONE);
        holder.Audio_Message_Layout.setVisibility(View.GONE);
        holder.LayoutMessageDoc.setVisibility(View.GONE);
        holder.TextMessageSend.setText(Messages.get(position)[0]);


        if (sendOrReciver == 0) {
            params1 = new RelativeLayout.LayoutParams
                    (RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);

            params1.resolveLayoutDirection(RelativeLayout.ALIGN_PARENT_END);
            params1.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);
            params1.setMarginStart(30);
            params1.setMarginEnd(70);
            holder.StatusMessageSend.setVisibility(View.GONE);
            holder.linearLayoutMessageStructure.setLayoutParams(params1);
            holder.linearLayoutMessageStructure.setBackgroundResource(R.drawable.gris);
            holder.TextMessageSend.setTextColor(Color.parseColor("#f7393838"));
        } else {

            params2 = new RelativeLayout.LayoutParams
                    (RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);

            params2.resolveLayoutDirection(RelativeLayout.ALIGN_PARENT_START);
            params2.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
            params2.setMarginEnd(30);
            params2.setMarginStart(70);

            holder.StatusMessageSend.setVisibility(View.VISIBLE);
            holder.linearLayoutMessageStructure.setLayoutParams(params2);
            holder.TextMessageSend.setTextColor(Color.parseColor("#ffffff"));
            holder.linearLayoutMessageStructure.setBackgroundResource(R.drawable.azul);
        }
    }

    private void CreateMessagePhotoVideo(int sendOrReciver, RecycleChatAdapter.ViewHolder holder, final int position) {

        holder.linearLayoutMessageStructure.setVisibility(View.GONE);
        holder.linearLayoutImageStructure.setVisibility(View.VISIBLE);
        holder.StatusMessageSend.setVisibility(View.GONE);
        holder.Audio_Message_Layout.setVisibility(View.GONE);
        holder.LayoutMessageDoc.setVisibility(View.GONE);

        if (sendOrReciver == 0) {
            params1 = new RelativeLayout.LayoutParams
                    (RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
            params1.resolveLayoutDirection(RelativeLayout.ALIGN_PARENT_END);
            params1.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);
            params1.setMarginStart(30);
            params1.setMarginEnd(200);
            holder.StatusMessageSendImage.setVisibility(View.GONE);
            holder.linearLayoutImageStructure.setBackgroundResource(R.drawable.gris);
            holder.linearLayoutImageStructure.setLayoutParams(params1);
        } else {
            params2 = new RelativeLayout.LayoutParams
                    (RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);

            params2.resolveLayoutDirection(RelativeLayout.ALIGN_PARENT_END);
            params2.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
            params2.setMarginEnd(30);
            params2.setMarginStart(200);
            holder.StatusMessageSendImage.setVisibility(View.VISIBLE);
            holder.linearLayoutImageStructure.setBackgroundResource(R.drawable.azul);
            holder.linearLayoutImageStructure.setLayoutParams(params2);
        }

        Glide.with(this.context)
                .load(Uri.fromFile(new File(Messages.get(position)[0])))
                .into(holder.ImageMessage);

        if (Messages.get(position)[2] == "2") {
            holder.PlayVideoIcon.setVisibility(View.GONE);
            holder.video_play_duration.setVisibility(View.GONE);

        } else if (Messages.get(position)[2] == "3") {
            holder.PlayVideoIcon.setVisibility(View.VISIBLE);
            holder.video_play_duration.setVisibility(View.VISIBLE);
        }

        holder.ImageMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ImageFullScreen_Main.class);
                intent.putExtra("image_path", Messages.get(position)[0]);
                intent.putExtra("profile_send_full", PathContact);
                intent.putExtra("Name_send_image", ContactName);
                intent.putExtra("type_media", (Messages.get(position)[2]=="3")?"v":"i");
                intent.putExtra("behavior" , behavior);
                v.getContext().startActivity(intent);
            }
        });

    }

    private void CreateMessageAudio(int sendOrReciver, final RecycleChatAdapter.ViewHolder holder, final int position) {

        if(Messages.get(position)[3]=="0")
        {
            createMessageAudio.createAudioControlMessageStructure();
            ASeekBar.add(createMessageAudio.getSeekBar());
            ImagePlayControl.add(new ImageView[]{createMessageAudio.getPlayImage(), createMessageAudio.getPauseImage()});
            TextViewRunTime.add(createMessageAudio.getTextRunTime());

            SeekPositionL = ASeekBar.size() - 1;
            Messages.get(position)[3] = String.valueOf(SeekPositionL);

            mediaPlayer = new MediaPlayer();
            holder.Play_Pause_Frame.addView(ImagePlayControl.get(Integer.parseInt(Messages.get(position)[3]))[0]);
            holder.Play_Pause_Frame.addView(ImagePlayControl.get(Integer.parseInt(Messages.get(position)[3]))[1]);
            holder.lls.addView(ASeekBar.get(Integer.parseInt(Messages.get(position)[3])));
            holder.Layout_Run_Time.addView(TextViewRunTime.get(Integer.parseInt(Messages.get(position)[3])));
        }else{
                    if(ImagePlayControl.get(Integer.parseInt(Messages.get(position)[3]))[0]
                            .getParent() != null) {
                        ((ViewGroup) ImagePlayControl.get(Integer.parseInt(Messages.get(position)[3]))[0]
                                .getParent()).removeView(ImagePlayControl.get(Integer.parseInt(Messages.get(position)[3]))[0]);
                    }
                    holder.Play_Pause_Frame.addView(ImagePlayControl.get(Integer.parseInt(Messages.get(position)[3]))[0]);

                    if(ImagePlayControl.get(Integer.parseInt(Messages.get(position)[3]))[1]
                            .getParent()!=null) {
                        ((ViewGroup) ImagePlayControl.get(Integer.parseInt(Messages.get(position)[3]))[1]
                                .getParent()).removeView(ImagePlayControl.get(Integer.parseInt(Messages.get(position)[3]))[1]);
                    }

                    holder.Play_Pause_Frame.addView(ImagePlayControl.get(Integer.parseInt(Messages.get(position)[3]))[1]);

                    if(ASeekBar.get(Integer.parseInt(Messages.get(position)[3]))
                            .getParent()!=null)
                    {
                        ((ViewGroup) ASeekBar.get(Integer.parseInt(Messages.get(position)[3]))
                                .getParent()).removeView(ASeekBar.get(Integer.parseInt(Messages.get(position)[3])));
                    }
                    holder.lls.addView(ASeekBar.get(Integer.parseInt(Messages.get(position)[3])));

                    if(TextViewRunTime.get(Integer.parseInt(Messages.get(position)[3]))
                            .getParent()!=null) {
                        ((ViewGroup) TextViewRunTime.get(Integer.parseInt(Messages.get(position)[3]))
                                .getParent()).removeView(TextViewRunTime.get(Integer.parseInt(Messages.get(position)[3])));
                    }
                    holder.Layout_Run_Time.addView(TextViewRunTime.get(Integer.parseInt(Messages.get(position)[3])));
            }

        holder.linearLayoutMessageStructure.setVisibility(View.GONE);
        holder.linearLayoutImageStructure.setVisibility(View.GONE);
        holder.LayoutMessageDoc.setVisibility(View.GONE);
        holder.Audio_Message_Layout.setVisibility(View.VISIBLE);
        TextViewRunTime.get(Integer.parseInt(Messages.get(position)[3])).setText(GetSongDuration(Messages.get(position)[0]));

        if (sendOrReciver == 0) {

            params1 = new RelativeLayout.LayoutParams
                    (RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);

            params1.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);
            params1.setMarginStart(30);
            params1.setMarginEnd(70);

            holder.Audio_Message_Layout.setLayoutParams(params1);
            holder.Audio_Message_Layout.setBackgroundResource(R.drawable.gris);
            holder.StatusMessageAudio.setVisibility(View.GONE);
        } else {

            params2 = new RelativeLayout.LayoutParams
                    (RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);

            params2.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
            params2.setMarginEnd(30);
            params2.setMarginStart(70);

            holder.Audio_Message_Layout.setLayoutParams(params2);
            holder.Audio_Message_Layout.setBackgroundResource(R.drawable.azul);
            holder.StatusMessageAudio.setVisibility(View.VISIBLE);
        }


        ImagePlayControl.get(Integer.parseInt(Messages.get(position)[3]))[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pathx =  Messages.get(position)[0];
                SeekPositionL = Integer.parseInt(Messages.get(position)[3]);
                StartAudio( );
            }
        });
        ImagePlayControl.get(Integer.parseInt(Messages.get(position)[3]))[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PauseAudio();
            }
        });

        ASeekBar.get(Integer.parseInt(Messages.get(position)[3])).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                seekChange(v, Integer.parseInt(Messages.get(position)[3]));
                return false;
            }
        });
    }

    private void CreateMessageDocument(int sendOrReciver , RecycleChatAdapter.ViewHolder holder, final int position) {

        holder.linearLayoutMessageStructure.setVisibility(View.GONE);
        holder.linearLayoutImageStructure.setVisibility(View.GONE);
        holder.Audio_Message_Layout.setVisibility(View.GONE);
        holder.LayoutMessageDoc.setVisibility(View.VISIBLE);

        File fileDocMessage = new File(Messages.get(position)[0]);
        holder.NameDocMessage.setText(fileDocMessage.getName());
        holder.SizeDocMessage.setText(String.valueOf( fileDocMessage.length()/1024)+" KB");

        if (sendOrReciver == 0) {
            params1 = new RelativeLayout.LayoutParams
                    (RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);

            params1.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);
            params1.setMarginStart(30);
            params1.setMarginEnd(70);

            holder.LayoutMessageDoc.setLayoutParams(params1);
            holder.LayoutMessageDoc.setBackgroundResource(R.drawable.gris);
            holder.StatusMessageDoc.setVisibility(View.GONE);
        } else {
            params2 = new RelativeLayout.LayoutParams
                    (RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);

            params2.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
            params2.setMarginEnd(30);
            params2.setMarginStart(70);

            holder.LayoutMessageDoc.setLayoutParams(params2);
            holder.LayoutMessageDoc.setBackgroundResource(R.drawable.azul);
            holder.StatusMessageDoc.setVisibility(View.VISIBLE);
        }

        holder.LayoutMessageDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenDocumentOnClick(Messages.get(position)[0] , GetMIMEDocument(Messages.get(position)[0]));
            }
        });
    }

    private void OpenDocumentOnClick(String Path , String MIME) {

        File file = new File(Path);
        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setDataAndType(Uri.fromFile(file), MIME);
        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        Intent intent = Intent.createChooser(target, context.getString(R.string.Open_File));
        try {
           this.context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this.context,"Please install a viewer for this file",Toast.LENGTH_SHORT);
        }
    }

    private String GetMIMEDocument(String Path) {
        String ext = FilenameUtils.getExtension(Path);
        String MIME ;

        switch(ext){
            case "pdf" : MIME="application/pdf";
                break;
            case "xlsx": MIME="application/vnd.ms-excel";
                break;
            case "csv" : MIME="application/vnd.ms-excel";
                break;
            case "ods" : MIME="application/vnd.ms-excel";
                break;
            case "txt" : MIME="text/plain";
                break;
            case "docx": MIME="text/plain";
                break;
            case "odt" : MIME="text/plain";
                break;
            default    : MIME="application/*";
        };


        return MIME;
    }

    private String GetSongDuration(String Path) {
        MediaPlayer mpDuration = new MediaPlayer();
        mpDuration = MediaPlayer.create(context ,Uri.fromFile(new File(Path)) );

        return getTimeString(mpDuration.getDuration());
    }

    private void MediaPlayerAsign( ) {

        mediaPlayer = MediaPlayer.create(context , Uri.fromFile(new File(Pathx)));
        ASeekBar.get(IsPlaying).setMax(mediaPlayer.getDuration());
        mediaPlayer.start();
        isCanceled=false;
        startPlayProgressUpdater(  );
    }

    private void StartAudio(  ) {

        ImagePlayControl.get(SeekPositionL)[1].setVisibility(View.VISIBLE);
        ImagePlayControl.get(SeekPositionL)[0].setVisibility(View.GONE);
        mediaPlayer = (mediaPlayer == null) ? new MediaPlayer() : mediaPlayer;

        if(IsPlaying != -1)
        {
            PauseAudio();
        }
        IsPlaying = SeekPositionL;
        MediaPlayerAsign(  );
    }

    private void PauseAudio() {
        TextViewRunTime.get(IsPlaying).setText( getTimeString( mediaPlayer.getDuration() ) );
        ImagePlayControl.get(IsPlaying)[1].setVisibility(View.GONE);
        ImagePlayControl.get(IsPlaying)[0].setVisibility(View.VISIBLE);
        isCanceled = true;
        mediaPlayer.release();
        mediaPlayer = null;
        ASeekBar.get(IsPlaying).setProgress(0);
        IsPlaying = -1;
    }

    private void seekChange(View v ,int IndexSender){
       if(!isCanceled) {
           if (mediaPlayer.isPlaying() && IndexSender == IsPlaying) {
               SeekBar sb = (SeekBar) v;
               mediaPlayer.seekTo(sb.getProgress());
           }
       }
    }

    private String getTimeString(long millis) {
        StringBuffer buf = new StringBuffer();

        int hours = (int) (millis / (1000 * 60 * 60));
        int minutes = (int) ((millis % (1000 * 60 * 60)) / (1000 * 60));
        int seconds = (int) (((millis % (1000 * 60 * 60)) % (1000 * 60)) / 1000);

        buf.append(String.format("%02d", hours))
                .append(":")
                .append(String.format("%02d", minutes))
                .append(":")
                .append(String.format("%02d", seconds));

        return buf.toString();
    }

    private void startPlayProgressUpdater( ) {

        if(!isCanceled) {

            if (mediaPlayer.isPlaying()) {
                ASeekBar.get(IsPlaying).setProgress(mediaPlayer.getCurrentPosition());
                TextViewRunTime.get(IsPlaying).setText( getTimeString( mediaPlayer.getCurrentPosition() ));
                notification = new Runnable() {
                    public void run()
                    {
                        startPlayProgressUpdater(  );
                    }
                };
                handler.postDelayed(notification, 1000);
            } else
            {
                mediaPlayer.pause();
                isCanceled=true;
                ImagePlayControl.get(IsPlaying)[0].setVisibility(View.VISIBLE);
                ImagePlayControl.get(IsPlaying)[1].setVisibility(View.GONE);
                ASeekBar.get(IsPlaying).setProgress(0);
                TextViewRunTime.get(IsPlaying).setText( getTimeString( mediaPlayer.getDuration() ));
            }
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView TextMessageSend;
        public TextView TimeMessageSend;
        public ImageView StatusMessageSend;
        public RelativeLayout linearLayoutMessageStructure;
        public LinearLayout TimeMessageLayout;
        public RelativeLayout linearLayoutImageStructure;
        public ImageView ImageMessage;
        public TextView TimeMessageSendImage;
        public LinearLayout TimeMessageLayoutImage;
        public ImageView StatusMessageSendImage;
        public ImageView PlayVideoIcon;
        public LinearLayout layout_video_play_duration;
        public TextView video_play_duration;
        public LinearLayout Audio_Message_Layout;
        public FrameLayout Play_Pause_Frame;
        public TextView TimeMessageAudioSend;
        public ImageView StatusMessageAudio;
        public LinearLayout lls;
        public LinearLayout Layout_Run_Time;
        public LinearLayout LayoutMessageDoc;
        public TextView NameDocMessage;
        public TextView SizeDocMessage;
        public TextView TimeMessageSendDoc;
        public ImageView StatusMessageDoc;

        public ViewHolder(View itemView) {
            super(itemView);
            this.TextMessageSend = (TextView) itemView.findViewById(R.id.text_chat_send);
            this.TimeMessageSend = (TextView) itemView.findViewById(R.id.Time_Message_Send);
            this.StatusMessageSend = (ImageView) itemView.findViewById(R.id.StatusMessage_send);
            this.linearLayoutMessageStructure = (RelativeLayout) itemView.findViewById(R.id.Message_structure_layout);
            this.TimeMessageLayout =(LinearLayout)itemView.findViewById(R.id.time_layout);
            this.linearLayoutImageStructure = (RelativeLayout) itemView.findViewById(R.id.MessageImageStructure);
            this.ImageMessage = (ImageView)itemView.findViewById(R.id.ImageMessage);
            this.TimeMessageSendImage =(TextView)itemView.findViewById(R.id.Time_Message_Send_Image);
            this.TimeMessageLayoutImage = (LinearLayout) itemView.findViewById(R.id.time_layout_Image);
            this.StatusMessageSendImage = (ImageView)itemView.findViewById(R.id.StatusMessage_send_Image);
            this.PlayVideoIcon = (ImageView) itemView.findViewById(R.id.play_icon_video);
            this.layout_video_play_duration =(LinearLayout) itemView.findViewById(R.id.layout_video_play_duration);
            this.video_play_duration = (TextView)itemView.findViewById(R.id.video_play_duration);
            this.Audio_Message_Layout = (LinearLayout)itemView.findViewById(R.id.Message_Audio_Layout);
            this.lls =(LinearLayout) itemView.findViewById(R.id.layout_seek_t);
            this.Play_Pause_Frame = (FrameLayout) itemView.findViewById(R.id.play_pause_layout);
            this.TimeMessageAudioSend = (TextView) itemView.findViewById(R.id.Time_Message_Send_Audio);
            this.StatusMessageAudio =(ImageView) itemView.findViewById(R.id.StatusMessage_send_Audio);
            this.Layout_Run_Time = (LinearLayout)itemView.findViewById(R.id.Layout_Run_Time);
            this.LayoutMessageDoc = (LinearLayout) itemView.findViewById(R.id.Message_Doc_Layout);
            this.NameDocMessage = (TextView) itemView.findViewById(R.id.DocumentName);
            this.SizeDocMessage = (TextView) itemView.findViewById(R.id.DocumentSize);
            this.TimeMessageSendDoc = (TextView)itemView.findViewById(R.id.Time_Message_Send_Doc);
            this.StatusMessageDoc = (ImageView)itemView.findViewById(R.id.StatusMessage_send_Doc);
        }
    }

    public class CreateMessageAudio
    {
        private Context context;
        private ImageView PlayImage;
        private ImageView PauseImage;
        private SeekBar seekBar;
        private ShapeDrawable thumb;
        private TextView textViewRunTime;

        public SeekBar getSeekBar() {
            return seekBar;
        }
        public ImageView getPauseImage() {
            return PauseImage;
        }
        public ImageView getPlayImage() {
            return PlayImage;
        }
        public TextView getTextRunTime(){return textViewRunTime; }

        public CreateMessageAudio(Context cnt) {
            this.context = cnt;
        }

        public void createAudioControlMessageStructure() {
            LinearLayout.LayoutParams PlayControl = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            seekBar = new SeekBar(this.context);
            PlayImage = new ImageView(this.context);
            PauseImage = new ImageView(this.context);

            PlayImage.setLayoutParams(PlayControl);
            PauseImage.setLayoutParams(PlayControl);
            PauseImage.setPadding(6, 6, 6, 6);
            PlayImage.setPadding(6, 6, 6, 6);
            PlayImage.setImageResource(R.drawable.btn_play);
            PauseImage.setImageResource(R.drawable.btn_pause);
            PauseImage.setVisibility(View.GONE);

            thumb = new ShapeDrawable(new OvalShape());

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            seekBar.setLayoutParams(lp);

            thumb.setIntrinsicHeight(35);
            thumb.setIntrinsicWidth(35);
            thumb.getPaint().setColor(Color.parseColor("#44a1f2"));

            seekBar.setThumb(thumb);
            seekBar.setVisibility(View.VISIBLE);
            seekBar.setBackgroundColor(Color.TRANSPARENT);

            seekBar.setProgressDrawable(this.context.getResources().getDrawable(R.drawable.progress_seek));

            LinearLayout.LayoutParams TextRunTimeLayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            TextRunTimeLayout.weight=1;
            textViewRunTime = new TextView(this.context);
            textViewRunTime.setEllipsize(TextUtils.TruncateAt.END);
            textViewRunTime.setTextSize(11);
            textViewRunTime.setText("00:00:00");
            textViewRunTime.setTextColor(Color.parseColor("#BDBDBD"));
            textViewRunTime.setLayoutParams(TextRunTimeLayout);
        }






    }


}
