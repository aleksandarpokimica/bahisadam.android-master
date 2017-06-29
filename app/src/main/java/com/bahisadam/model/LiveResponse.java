package com.bahisadam.model;

import java.util.*;

/**
 * Created by atata on 13/12/2016.
 */

public class LiveResponse {
    public List<LiveItem> items;

    public LiveResponse() {
        items = new LinkedList<LiveItem>();
    }

    public class LiveItem{
        Integer id;
        String flag;
        private String league_name;
        private String league_name_tr;
        String country;
        Integer order;
        List<MatchPOJO.Match> matches = new List<MatchPOJO.Match>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<MatchPOJO.Match> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(MatchPOJO.Match match) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends MatchPOJO.Match> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends MatchPOJO.Match> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public MatchPOJO.Match get(int index) {
                return null;
            }

            @Override
            public MatchPOJO.Match set(int index, MatchPOJO.Match element) {
                return null;
            }

            @Override
            public void add(int index, MatchPOJO.Match element) {

            }

            @Override
            public MatchPOJO.Match remove(int index) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @Override
            public ListIterator<MatchPOJO.Match> listIterator() {
                return null;
            }

            @Override
            public ListIterator<MatchPOJO.Match> listIterator(int index) {
                return null;
            }

            @Override
            public List<MatchPOJO.Match> subList(int fromIndex, int toIndex) {
                return null;
            }
        };

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getLeague_name() {
            return league_name;
        }

        public void setLeague_name(String league_name) {
            this.league_name = league_name;
        }

        public String getLeague_name_tr() {
            return league_name_tr;
        }

        public void setLeague_name_tr(String league_name_tr) {
            this.league_name_tr = league_name_tr;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public Integer getOrder() {
            return order;
        }

        public void setOrder(Integer order) {
            this.order = order;
        }

        public List<MatchPOJO.Match> getMatches() {
            return matches;
        }

        public void setMatches(List<MatchPOJO.Match> matches) {
            this.matches = matches;
        }
    }

}
