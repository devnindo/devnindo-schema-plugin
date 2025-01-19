package performance;

import io.devnindo.datatype.json.JsonObject;
import io.devnindo.datatype.json.jackson.JacksonCodec;
import io.devnindo.datatype.schema.BeanSchema;
import io.devnindo.datatype.schema.DataBean;
import io.devnindo.datatype.tuples.Pair;
import org.gradle.internal.impldep.com.fasterxml.jackson.core.JsonProcessingException;
import org.gradle.internal.impldep.com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class SchemaPerformanceTest
{
    public static final Random random = new Random();
    public static final ObjectMapper JACKSON_MAPPER = new ObjectMapper();
    public static final Function<Runnable, Long> TIME_COMPUTE = (r)->{
        Long start = System.nanoTime();
        r.run();
        return System.nanoTime() - start;
    };

    @Test
    public void test_serialization(){

        runTest("# WARM UP", 50, false);
        runTest("# LARGE SAMPLE", 100, false);
        runTest("# LARGE HEAVY SAMPLE", 300, true);


    }

    public static void runTest(String stage, Integer sampleSize, boolean heavyPayload)
    {
        List<String> warmUpSample = generateDataSample(sampleSize, heavyPayload);
        List<Pair<Runnable, Runnable>> runnablePair = generateRunnablePair(warmUpSample);

        int nindoLoosing = 0, nindoWinning = 0;
        long maxWinMargin = 0, maxLoosingMargin = 0;
        for(Pair<Runnable, Runnable> pair: runnablePair)
        {
            Long nindoTime = TIME_COMPUTE.apply(pair.first);
            Long jacksonTime = TIME_COMPUTE.apply(pair.second);

            if(jacksonTime < nindoTime){
                nindoLoosing++;
                long diff = nindoTime - jacksonTime;
                if(diff > maxLoosingMargin)
                    maxLoosingMargin = diff;

            }
            else if(nindoTime < jacksonTime)
            {
                nindoWinning++;
                long diff = jacksonTime - nindoTime;
                if(diff > maxWinMargin)
                    maxWinMargin = diff;
            }
        }

        System.out.println("Running: "+stage);
        System.out.println("For sample size:"+ sampleSize);
        System.out.printf("\t ObjectMapper won %d iteration with max diff margin %d\n", nindoLoosing, maxLoosingMargin);
        System.out.printf("\t Schema-GEN won %d iteration with max diff margin %d\n", nindoWinning, maxWinMargin);


    }

    public static String generateRandomString() {
        String candidateChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        Integer length = random.nextInt(200);
        StringBuilder sb = new StringBuilder ();
        Random random = new Random ();
        for (int i = 0; i < length; i ++) {
            int needle = random.nextInt (candidateChars.length ());
            sb.append (candidateChars.charAt(needle));
        }

        return sb.toString ();
    }

    public static List<String> generateDataSample(int count, boolean heavy)
    {
        List<String> sampleList = new ArrayList<>();
        for(int idx =0; idx < count; idx++)
        {
            JsonObject json = new JsonObject();
            List<JsonObject> antList = new ArrayList<>();
            AnEnum[] enumList = AnEnum.values();

            int antCount = heavy ? 100 : 10;
            for(int item=0; item < antCount; item++)
            {
                JsonObject antJson = new JsonObject();
                antJson
                        .put($AntBean.ANT_INTEGER, random.nextInt())
                        .put($AntBean.ANT_LONG, random.nextLong())
                        .put($AntBean.ANT_STRING, generateRandomString())
                        .put($AntBean.ANT_ENUM, enumList[random.nextInt(enumList.length)]);


                antList.add(antJson);
            }

            json
                    .put($RootBean.FLD_INTEGER, random.nextInt())
                    .put($RootBean.FLD_LONG, random.nextLong(1, Long.MAX_VALUE))
                    .put($RootBean.FLD_BOOLEAN, random.nextBoolean())
                    .put($RootBean.FLD_STRING, generateRandomString())
                    .put($RootBean.FLD_DOUBLE, random.nextDouble())
                    .put($RootBean.FLD_ANT_LIST, antList);

            sampleList.add(json.toString());
        }


        return sampleList;
    }

    public static List<Pair<Runnable, Runnable>> generateRunnablePair(List<String> dataSample){
        return dataSample
                .stream()
                .map(item -> {
                    Runnable nindoStyle = ()-> {
                        JsonObject json = new JsonObject(item);
                        RootBean rootBean = json.toBean(RootBean.class);
                    };

                    Runnable jacksonStyle = () -> {
                        try {
                            // intentionally two step: string -> map -> bean
                            // real world case for vertx based application to work with dynamic data
                            //Map<String, Object> jsMap = JACKSON_MAPPER.readValue(item, Map.class);
                            RootBean rootBean = JACKSON_MAPPER.readValue(item, RootBean.class);

                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                    };
                    return  Pair.of(nindoStyle, jacksonStyle);
                })
                .collect(Collectors.toList());
    }



}
