package persistence;

import org.json.JSONObject;

//CITATION: Carter, Paul (2021) JSON Serialization Demo (Version 20210307) [Source Code].
//https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/commit/d31979d8a993d63c3a8c13c8add7f9d1753777b6.
public interface Writable {
    //returns this as a JSON Object
    JSONObject toJson();
}
