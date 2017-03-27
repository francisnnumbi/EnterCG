package fnn.smirl.cardgame.object.core.interfacing;
import android.graphics.Point;
import android.graphics.PointF;

public class Vector2D{
 private PointF p;
 public Vector2D(PointF p) {
	this.p = p;
 }

 public Vector2D(float x, float y) {
	this(new PointF(x, y));
 }

 public void add(Vector2D v) {
	p.x += v.p.x;
	p.y += v.p.y;
 }

 public void subtract(Vector2D v) {
	p.x -= v.p.x;
	p.y -= v.p.y;
 }


 public void multiply(float scalar) {
	p.x *= scalar;
	p.y *= scalar;
 }

 private float length() {
	return (float)Math.sqrt(p.x * p.x + p.y * p.y);
 }

 public static float length(Vector2D v) {
	return v.length();
 }

 public static float distance(Vector2D v0, Vector2D v1) {
	Vector2D vd = copy(v1);
	vd.subtract(v0);
	return length(vd);
 }
 
 public Vector2D normalize(){
	return new Vector2D(p.x/length(), p.y/length());
 }
 
 public static Vector2D normalize(Vector2D v0, Vector2D v1){
	float dist = distance(v0, v1);
	Vector2D tp = Vector2D.copy(v1);
	tp.subtract(v0);
	tp.multiply(1/dist);
	return tp;
 }
 
 public void position(Vector2D v){
	p.set(v.p.x, v.p.y);
 }
 
 public void move(Vector2D dest, float speed, float rate){
	float ratio = (speed * rate);
	Vector2D vd = Vector2D.normalize(this, dest);
	vd.multiply(ratio);
	if(Vector2D.distance(this, vd) < Vector2D.distance(this, dest)){
	 position(vd);
	}else{
	 position(dest);
	}
 }
 
 public void move_2(Vector2D dest, float speed, float rate){
	float ratio = (speed * rate)/distance(this, dest);
	Vector2D vd = Vector2D.copy(this);
	vd.multiply(ratio);
	if(ratio > speed){
	 position(vd);
	}else{
	 position(dest);
	}
 }

 public static Vector2D copy(Vector2D v) {
	return new Vector2D(v.p.x, v.p.y);
 }
}
